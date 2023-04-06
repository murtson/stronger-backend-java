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
    public void deleteByLoggedExerciseId(Long loggedExerciseId) {
        loggedSetRepository.deleteAllByLoggedExerciseId(loggedExerciseId);
    }

    @Override
    public List<LoggedSet> getByRepsAndExerciseAndUserId(int reps, Long exerciseId, Long userId) {
        // should you get all and THEN sort, or sort them from the query?
        return loggedSetRepository.findByRepsAndLoggedExercise_Exercise_IdAndLoggedExercise_Workout_User_Id(reps, exerciseId, userId);
    }

    @Override
    public List<LoggedSet> createLoggedSet(Long loggedExerciseId, LoggedSet loggedSet) {
        LoggedExercise loggedExercise = loggedExerciseService.getLoggedExercise(loggedExerciseId);
        List<LoggedSet> previousLoggedSets = loggedExercise.getLoggedSets();

        loggedSet.setLoggedExercise(loggedExercise);

        updateExercisePrIfImproved(loggedExercise, loggedSet);

        updateTopLoggedSetIfImproved(loggedSet, previousLoggedSets);

        saveLoggedSet(loggedSet);

        List<LoggedSet> response = new ArrayList<>(previousLoggedSets);
        response.add(loggedSet);
        return response;

    }

    public void updateTopLoggedSetIfImproved(LoggedSet loggedSetToBeCompare, List<LoggedSet> previousLoggedSets) {
        boolean isNewTopSet = isNewTopLoggedSet(loggedSetToBeCompare, previousLoggedSets);
        if(!isNewTopSet) return;

        LoggedSet previousTopSet = getTopLoggedSet(previousLoggedSets);
        if(previousTopSet != null) {
            previousTopSet.setTopLoggedSet(false);
            saveLoggedSet(previousTopSet);
        };

        loggedSetToBeCompare.setTopLoggedSet(true);
    }

    @Override
    public boolean isNewTopLoggedSet(LoggedSet newLoggedSet, List<LoggedSet> previousLoggedSets) {
        double previousTopSet = previousLoggedSets.stream()
                .mapToDouble(LoggedSet::getEstimatedOneRepMax)
                .max()
                .orElse(Double.NEGATIVE_INFINITY);

        return previousTopSet < newLoggedSet.getEstimatedOneRepMax();
    }

    @Override
    public LoggedSet getTopLoggedSet(List<LoggedSet> loggedSets) {
        return (loggedSets.isEmpty()) ? null : loggedSets
                .stream()
                .max(Comparator.comparing(LoggedSet::getEstimatedOneRepMax))
                .get();
    }
    @Override
    public void updateExercisePrIfImproved(LoggedExercise loggedExercise, LoggedSet loggedSet) {

        Long exerciseId = loggedExercise.getExercise().getId();
        Long userId = loggedExercise.getWorkout().getUser().getId();
        ExercisePr currentExercisePr = exercisePrService.getByRepsAndExerciseAndUserId(
                loggedSet.getReps(),
                exerciseId,
                userId
        );

        if(currentExercisePr == null) {
            ExercisePr newExercisePr = ExercisePr.from(loggedSet);
            loggedSet.setExercisePr(newExercisePr);
            return;
        }

        Double currentEORM = currentExercisePr.getEstimatedOneRepMax();
        Double requestEORM = loggedSet.getEstimatedOneRepMax();
        boolean isNotANewExercisePr = Double.compare(currentEORM, requestEORM) >= 0;

        if(isNotANewExercisePr) return;

        exercisePrService.deleteExercisePr(currentExercisePr);
        ExercisePr newExercisePr = ExercisePr.from(loggedSet);
        loggedSet.setExercisePr(newExercisePr);

    }

}


//    @Override
//    public List<LoggedSet> updateLoggedSets(Long loggedExerciseId, List<LoggedSet> loggedSets) {
//
//        LoggedExercise loggedExercise = loggedExerciseService.getLoggedExercise(loggedExerciseId);
//        Long exerciseId = loggedExercise.getExercise().getId();
//        Long userId = loggedExercise.getWorkout().getUser().getId();
//
//        List<Integer> repsToBeEvaluated = new ArrayList<>();
//        loggedExercise.getLoggedSets().forEach(set -> {
//            if(set.getExercisePr() != null) repsToBeEvaluated.add(set.getReps());
//        });
//
//        loggedExercise.getLoggedSets().clear();
//        loggedExerciseService.save(loggedExercise);
//
//        repsToBeEvaluated.forEach(rep -> {
//            List<LoggedSet> allLoggedSetsByReps = getByRepsAndExerciseAndUserId(rep, exerciseId, userId);
//            LoggedSet topLoggedSet = getTopLoggedSet(allLoggedSetsByReps);
//            LoggedSet updatedLoggedSet = setExercisePr(topLoggedSet);
//            saveLoggedSet(updatedLoggedSet);
//        });
//
//        //deleteByLoggedExerciseId(loggedExercise.getId());
//        loggedSets.forEach(set -> set.setLoggedExercise(loggedExercise));
//        LoggedSet topLoggedSet = getTopLoggedSet(loggedSets);
//        topLoggedSet.setTopLoggedSet(true);
//
//        return loggedSets.stream()
//                .map(this::configureExercisePr)
//                .map(this::saveLoggedSet)
//                .toList();
//
//    }


//    private LoggedSet configureExercisePr(LoggedSet loggedSet) {
//        Integer reps = loggedSet.getReps();
//        Long exerciseId = loggedSet.getLoggedExercise().getExercise().getId();
//        Long userId = loggedSet.getLoggedExercise().getWorkout().getUser().getId();
//        ExercisePr currentExercisePR = exercisePrService.getByRepsAndExerciseAndUserId(reps, exerciseId, userId);
//
//        if(currentExercisePR == null) {
//            System.out.println("No current PR");
//            return this.setExercisePr(loggedSet);
//        };
//
//        Double currentEORM = currentExercisePR.getEstimatedOneRepMax();
//        Double setEORM = loggedSet.getEstimatedOneRepMax();
//
//        boolean isNotANewPersonalRecord = Double.compare(currentEORM, setEORM) >= 0;
//        if(isNotANewPersonalRecord) {
//            System.out.println("current: " + currentEORM);
//            System.out.println("set: " + setEORM);
//            System.out.println("Is NOT a new PR");
//            return loggedSet;
//        }
//
//
//        System.out.println("Is a new PR");
//        System.out.println("delete this: " + currentExercisePR.getId());
//
//        LoggedSet loggedSetWithCurrentPr = currentExercisePR.getLoggedSet();
//        // Seems to have something to do with one-to-one
//        loggedSetWithCurrentPr.setExercisePr(null);
//        exercisePrService.deleteExercisePR(currentExercisePR.getId());
//        // saveLoggedSet(currentPrLoggedSet);
//
//        return setExercisePr(loggedSet);
//    }
