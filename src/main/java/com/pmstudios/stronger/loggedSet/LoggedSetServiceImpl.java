package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.exercisePr.ExercisePrService;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedExercise.LoggedExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@AllArgsConstructor
@Service
public class LoggedSetServiceImpl implements LoggedSetService {

    LoggedSetRepository loggedSetRepository;
    ExercisePrService exercisePrService;
    LoggedExerciseService loggedExerciseService;


    @Override
    public LoggedSet saveLoggedSet(LoggedSet set) {
        return loggedSetRepository.save(set);
    }

    @Override
    public List<LoggedSet> updateLoggedSets(Long loggedExerciseId, List<LoggedSet> loggedSets) {

        LoggedExercise loggedExercise = loggedExerciseService.getLoggedExercise(loggedExerciseId);

        boolean containsPr = checkForPersonalRecord(loggedExercise.getLoggedSets());


//        if(containsPr) {
//            for (LoggedSet loggedSet : loggedExercise.getLoggedSets()) {
//                if(loggedSet.getExercisePr() != null) {
//                    Integer reps = loggedSet.getReps();
//                    Long exerciseId = loggedSet.getLoggedExercise().getExercise().getId();
//                    Long userId = loggedSet.getLoggedExercise().getWorkout().getUser().getId();
//
//                    List<LoggedSet> allLoggedSets = getByRepsAndExerciseAndUserId(reps, exerciseId, userId);
//                    System.out.println("SIZE: " + allLoggedSets.size());
//                    LoggedSet bestLoggedSet = getBestLoggedSet(allLoggedSets);
//                    LoggedSet updatedLoggedSet = setExercisePR(bestLoggedSet);
//                    saveLoggedSet(updatedLoggedSet);
//                }
//            }
//        }

        loggedExercise.getLoggedSets().clear();
        loggedExerciseService.save(loggedExercise);
        //deleteByLoggedExerciseId(loggedExercise.getId());
        loggedSets.forEach(set -> set.setLoggedExercise(loggedExercise));
        return loggedSets.stream()
                .map(this::configureExercisePr)
                .map(this::saveLoggedSet)
                .toList();

    }

    @Override
    public void deleteByLoggedExerciseId(Long loggedExerciseId) {
        loggedSetRepository.deleteAllByLoggedExerciseId(loggedExerciseId);
    }

    @Override
    public LoggedSet getBestLoggedSet(List<LoggedSet> loggedSets) {
        return (loggedSets.isEmpty()) ? null : loggedSets
                .stream()
                .max(Comparator.comparing(LoggedSet::getEstimatedOneRepMax))
                .get();
    }

    @Override
    public List<LoggedSet> getByRepsAndExerciseAndUserId(int reps, Long exerciseId, Long userId) {
        // should you get all and THEN sort, or sort them from the query?
        return loggedSetRepository.findByRepsAndLoggedExercise_Exercise_IdAndLoggedExercise_Workout_User_Id(reps, exerciseId, userId);
    }

    public boolean checkForPersonalRecord(List<LoggedSet> loggedSets) {
        for (LoggedSet loggedSet : loggedSets) if (loggedSet.getExercisePr() != null) return true;
        return false;
    }


    private LoggedSet configureExercisePr(LoggedSet loggedSet) {
        Integer reps = loggedSet.getReps();
        Long exerciseId = loggedSet.getLoggedExercise().getExercise().getId();
        Long userId = loggedSet.getLoggedExercise().getWorkout().getUser().getId();
        ExercisePr currentExercisePR = exercisePrService.getByRepsAndExerciseAndUserId(reps, exerciseId, userId);

        if(currentExercisePR == null) {
            System.out.println("No current PR");
            return this.setExercisePR(loggedSet);
        };

        Double currentEORM = currentExercisePR.getEstimatedOneRepMax();
        Double setEORM = loggedSet.getEstimatedOneRepMax();

        boolean isNotANewPersonalRecord = Double.compare(currentEORM, setEORM) >= 0;
        if(isNotANewPersonalRecord) {
            System.out.println("current: " + currentEORM);
            System.out.println("set: " + setEORM);
            System.out.println("Is NOT a new PR");
            return loggedSet;
        }


        System.out.println("Is a new PR");
        System.out.println("delete this: " + currentExercisePR.getId());

        LoggedSet loggedSetWithCurrentPr = currentExercisePR.getLoggedSet();
        // Seems to have something to do with one-to-one
        loggedSetWithCurrentPr.setExercisePr(null);
        exercisePrService.deleteExercisePR(currentExercisePR.getId());
        // saveLoggedSet(currentPrLoggedSet);

        return this.setExercisePR(loggedSet);
    }

    private LoggedSet setExercisePR(LoggedSet loggedSet) {
        ExercisePr exercisePR = new ExercisePr(
                loggedSet.getWeight(), loggedSet.getReps(),
                loggedSet.getEstimatedOneRepMax(), loggedSet.getLoggedExercise().getWorkout().getUser(),
                loggedSet.getLoggedExercise().getExercise(), loggedSet);

        loggedSet.setExercisePr(exercisePR);
        return loggedSet;
    }




}
