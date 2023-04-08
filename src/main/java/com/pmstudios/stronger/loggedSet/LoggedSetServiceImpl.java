package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.exercisePr.ExercisePrService;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedExercise.LoggedExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@AllArgsConstructor
@Service
public class LoggedSetServiceImpl implements LoggedSetService {

    LoggedSetRepository loggedSetRepository;
    ExercisePrService exercisePrService;
    LoggedExerciseService loggedExerciseService;

    @Override
    public LoggedSet getById(Long id) {
        return loggedSetRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, LoggedSet.class));
    }

    @Override
    public LoggedSet save(LoggedSet set) {
        return loggedSetRepository.save(set);
    }

    @Override
    public void delete(LoggedSet loggedSet) {
        loggedSetRepository.delete(loggedSet);
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

    @Transactional
    @Override
    public List<LoggedSet> addLoggedSet(Long loggedExerciseId, LoggedSet loggedSet) {
        LoggedExercise loggedExercise = loggedExerciseService.getLoggedExercise(loggedExerciseId);
        List<LoggedSet> previousLoggedSets = loggedExercise.getLoggedSets();

        loggedSet.setLoggedExercise(loggedExercise);

        updateExercisePrWhenLoggedSetIsAdded(loggedExercise, loggedSet);

        updateTopLoggedSetWhenLoggedSetIsAdded(loggedSet, previousLoggedSets);

        save(loggedSet);

        List<LoggedSet> response = new ArrayList<>(previousLoggedSets);
        response.add(loggedSet);
        return response;

    }

    private void updateTopLoggedSetWhenLoggedSetIsAdded(LoggedSet loggedSetToBeAdded, List<LoggedSet> previousLoggedSets) {
        boolean isNewTopSet = isNewTopLoggedSet(loggedSetToBeAdded, previousLoggedSets);
        if(!isNewTopSet) return;

        LoggedSet previousTopSet = getLoggedSetWithHighestEORM(previousLoggedSets);
        if(previousTopSet != null) {
            previousTopSet.setTopLoggedSet(false);
            save(previousTopSet);
        };

        loggedSetToBeAdded.setTopLoggedSet(true);
    }

    private void updateExercisePrWhenLoggedSetIsAdded(LoggedExercise loggedExercise, LoggedSet loggedSet) {

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

    @Transactional
    @Override
    public List<LoggedSet> removeLoggedSet(Long loggedSetId) {

        LoggedSet loggedSetToBeRemoved = getById(loggedSetId);

        Integer repsToEvaluate = loggedSetToBeRemoved.getReps();
        Long exerciseIdToEvaluate = loggedSetToBeRemoved.getLoggedExercise().getExercise().getId();
        Long userId = loggedSetToBeRemoved.getLoggedExercise().getWorkout().getUser().getId();

        Long loggedExerciseId = loggedSetToBeRemoved.getLoggedExercise().getId();

        boolean shouldUpdateExercisePr = loggedSetToBeRemoved.getExercisePr() != null;
        boolean shouldUpdateTopLoggedSet = loggedSetToBeRemoved.isTopLoggedSet();

        delete(loggedSetToBeRemoved);

        List<LoggedSet> loggedSetsAfterDeletion = loggedExerciseService
                .getLoggedExercise(loggedExerciseId)
                .getLoggedSets();

        if(shouldUpdateExercisePr) {
            updateExercisePrWhenLoggedSetIsDeleted(repsToEvaluate, exerciseIdToEvaluate, userId);
        }

        if(shouldUpdateTopLoggedSet) {
            updateTopLoggedSetWhenLoggedSetIsDeleted(loggedSetsAfterDeletion);
        }

        return loggedSetsAfterDeletion;
    }

    private void updateExercisePrWhenLoggedSetIsDeleted(Integer reps, Long exerciseId, Long userId) {

        List<LoggedSet> historyLoggedSets = getByRepsAndExerciseAndUserId(reps, exerciseId, userId);

        if(historyLoggedSets.isEmpty()) return;

        LoggedSet loggedSetToUpdate = getLoggedSetWithHighestEORM(historyLoggedSets);
        ExercisePr newExercisePr = ExercisePr.from(loggedSetToUpdate);

        loggedSetToUpdate.setExercisePr(newExercisePr);
        save(loggedSetToUpdate);
    }

    private void updateTopLoggedSetWhenLoggedSetIsDeleted(List<LoggedSet> loggedSets) {
        if(loggedSets.isEmpty()) return;
        LoggedSet loggedSetToUpdate = getLoggedSetWithHighestEORM(loggedSets);
        loggedSetToUpdate.setTopLoggedSet(true);
        save(loggedSetToUpdate);
    }

    private boolean isNewTopLoggedSet(LoggedSet newLoggedSet, List<LoggedSet> previousLoggedSets) {
        double previousTopSet = previousLoggedSets.stream()
                .mapToDouble(LoggedSet::getEstimatedOneRepMax)
                .max()
                .orElse(Double.NEGATIVE_INFINITY);

        return previousTopSet < newLoggedSet.getEstimatedOneRepMax();
    }

    private LoggedSet getLoggedSetWithHighestEORM(List<LoggedSet> loggedSets) {
        return (loggedSets.isEmpty()) ? null : loggedSets
                .stream()
                .max(Comparator.comparing(LoggedSet::getEstimatedOneRepMax))
                .get();
    }

}
