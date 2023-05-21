package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.NumberUtility;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.exercisePr.ExercisePrService;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedExercise.LoggedExerciseService;
import com.pmstudios.stronger.loggedSet.dto.AddLoggedSetRequest;
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
    public List<LoggedSet> addLoggedSet(LoggedExercise loggedExercise, LoggedSet toBeAddedLoggedSet) {
        List<LoggedSet> previousLoggedSets = loggedExercise.getLoggedSets();

        toBeAddedLoggedSet.setLoggedExercise(loggedExercise);

        updateExercisePrWhenLoggedSetIsAdded(loggedExercise, toBeAddedLoggedSet);

        updateTopLoggedSetWhenLoggedSetIsAdded(toBeAddedLoggedSet, previousLoggedSets);

        save(toBeAddedLoggedSet);

        List<LoggedSet> response = new ArrayList<>(previousLoggedSets);
        response.add(toBeAddedLoggedSet);
        return response;

    }

    public void updateExercisePrWhenLoggedSetIsAdded(LoggedExercise loggedExercise, LoggedSet toBeAddedLoggedSet) {

        Long exerciseId = loggedExercise.getExercise().getId();
        Long userId = loggedExercise.getWorkout().getUser().getId();
        ExercisePr currentExercisePr = exercisePrService.getByRepsAndExerciseAndUserId(
                toBeAddedLoggedSet.getReps(),
                exerciseId,
                userId
        );

        if (currentExercisePr == null) {
            ExercisePr newExercisePr = ExercisePr.from(toBeAddedLoggedSet);
            toBeAddedLoggedSet.setExercisePr(newExercisePr);
            return;
        }

        Double currentEORM = currentExercisePr.getEstimatedOneRepMax();
        Double requestEORM = toBeAddedLoggedSet.getEstimatedOneRepMax();
        boolean isNotANewExercisePr = Double.compare(currentEORM, requestEORM) >= 0;

        if (isNotANewExercisePr) return;

        exercisePrService.delete(currentExercisePr);
        ExercisePr newExercisePr = ExercisePr.from(toBeAddedLoggedSet);
        toBeAddedLoggedSet.setExercisePr(newExercisePr);

    }

    public void updateTopLoggedSetWhenLoggedSetIsAdded(LoggedSet loggedSetToBeAdded, List<LoggedSet> previousLoggedSets) {
        boolean isNewTopSet = isNewTopLoggedSet(loggedSetToBeAdded, previousLoggedSets);
        if (!isNewTopSet) return;

        getLoggedSetWithHighestEORM(previousLoggedSets).ifPresent(previousTopSet -> {
            previousTopSet.setTopLoggedSet(false);
            save(previousTopSet);
        });

        loggedSetToBeAdded.setTopLoggedSet(true);
    }

    @Transactional
    @Override
    public List<LoggedSet> remove(LoggedSet loggedSetToBeRemoved) {
        Integer repsToEvaluate = loggedSetToBeRemoved.getReps();
        Long exerciseIdToEvaluate = loggedSetToBeRemoved.getLoggedExercise().getExercise().getId();
        Long userId = loggedSetToBeRemoved.getLoggedExercise().getWorkout().getUser().getId();

        Long loggedExerciseId = loggedSetToBeRemoved.getLoggedExercise().getId();

        boolean shouldUpdateExercisePr = loggedSetToBeRemoved.getExercisePr() != null;
        boolean shouldUpdateTopLoggedSet = loggedSetToBeRemoved.isTopLoggedSet();

        delete(loggedSetToBeRemoved);

        List<LoggedSet> loggedSetsAfterDeletion = loggedExerciseService.getById(loggedExerciseId).getLoggedSets();

        if (shouldUpdateExercisePr) {
            updateExercisePrWhenLoggedSetIsDeleted(repsToEvaluate, exerciseIdToEvaluate, userId);
        }

        if (shouldUpdateTopLoggedSet) {
            updateTopLoggedSetWhenLoggedSetIsDeleted(loggedSetsAfterDeletion);
        }

        return loggedSetsAfterDeletion;
    }

    public void updateExercisePrWhenLoggedSetIsDeleted(Integer repsToEvaluate, Long exerciseId, Long userId) {

        List<LoggedSet> historyLoggedSets = getByRepsAndExerciseAndUserId(repsToEvaluate, exerciseId, userId);

        if (historyLoggedSets.isEmpty()) return;

        LoggedSet loggedSetToUpdate = getLoggedSetWithHighestEORM(historyLoggedSets).get();
        ExercisePr newExercisePr = ExercisePr.from(loggedSetToUpdate);

        loggedSetToUpdate.setExercisePr(newExercisePr);
        save(loggedSetToUpdate);
    }

    public void updateTopLoggedSetWhenLoggedSetIsDeleted(List<LoggedSet> loggedSetsAfterDeletion) {
        getLoggedSetWithHighestEORM(loggedSetsAfterDeletion).ifPresent(loggedSetToUpdate -> {
            loggedSetToUpdate.setTopLoggedSet(true);
            save(loggedSetToUpdate);
        });
    }

    public boolean isNewTopLoggedSet(LoggedSet newLoggedSet, List<LoggedSet> previousLoggedSets) {
        double previousTopSet = previousLoggedSets.stream()
                .mapToDouble(LoggedSet::getEstimatedOneRepMax)
                .max()
                .orElse(Double.NEGATIVE_INFINITY);

        return previousTopSet < newLoggedSet.getEstimatedOneRepMax();

    }

    // what happens if two exercises have the same EORM?
    public Optional<LoggedSet> getLoggedSetWithHighestEORM(List<LoggedSet> loggedSets) {
        return loggedSets.stream()
                .max(Comparator.comparing(LoggedSet::getEstimatedOneRepMax));
    }

}
