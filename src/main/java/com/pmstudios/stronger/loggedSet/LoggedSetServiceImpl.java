package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.exercisePr.ExercisePrService;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedExercise.LoggedExerciseService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LoggedSetServiceImpl implements LoggedSetService {

    private final LoggedSetRepository loggedSetRepository;
    private final ExercisePrService exercisePrService;
    private final LoggedExerciseService loggedExerciseService;

    @Autowired
    LoggedSetServiceImpl(LoggedSetRepository loggedSetRepository,
                         ExercisePrService exercisePrService,
                         LoggedExerciseService loggedExerciseService) {
        this.loggedSetRepository = loggedSetRepository;
        this.exercisePrService = exercisePrService;
        this.loggedExerciseService = loggedExerciseService;
    }


    @Override
    public LoggedSet getById(Long id) {
        return loggedSetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, LoggedSet.class));
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

    // @Transactional
    // This cannot be Transactional because of the constraint that there can only be 1 exercisePr for a specific exercise and rep-range.
    @Override
    public List<LoggedSet> addLoggedSet(LoggedExercise loggedExercise, LoggedSet toBeAddedLoggedSet) {
        List<LoggedSet> previousLoggedSets = Optional.ofNullable(loggedExercise.getLoggedSets()).orElse(List.of());

        toBeAddedLoggedSet.setLoggedExercise(loggedExercise);

        updateExercisePrWhenLoggedSetIsAdded(toBeAddedLoggedSet);

        updateTopLoggedSetWhenLoggedSetIsAdded(toBeAddedLoggedSet, previousLoggedSets);

        save(toBeAddedLoggedSet);

        List<LoggedSet> response = new ArrayList<>(previousLoggedSets);
        response.add(toBeAddedLoggedSet);
        return response;

    }

    @Override
    public List<LoggedSet> removeLoggedSet(LoggedSet loggedSetToBeRemoved) {
        Integer repsToEvaluate = loggedSetToBeRemoved.getReps();
        Long exerciseIdToEvaluate = loggedSetToBeRemoved.getLoggedExercise().getExercise().getId();
        Long userId = loggedSetToBeRemoved.getLoggedExercise().getWorkout().getUser().getId();
        Long loggedExerciseId = loggedSetToBeRemoved.getLoggedExercise().getId();

        boolean shouldUpdateExercisePr = loggedSetToBeRemoved.getExercisePr() != null;
        boolean shouldUpdateTopLoggedSet = loggedSetToBeRemoved.isTopLoggedSet();

        delete(loggedSetToBeRemoved);

        if (shouldUpdateExercisePr) {
            List<LoggedSet> historyLoggedSets = getByRepsAndExerciseAndUserId(repsToEvaluate, exerciseIdToEvaluate, userId);
            updateNewExercisePrFromOldSets(historyLoggedSets);
        }

        // Can be empty (if deletion was last element)
        // Fix this if you want method to be transactional
        List<LoggedSet> loggedSetsAfterDeletion = loggedExerciseService.getById(loggedExerciseId).getLoggedSets();

        if (shouldUpdateTopLoggedSet && !loggedSetsAfterDeletion.isEmpty()) {
            LoggedSet newTopLoggedSet = getLoggedSetWithHighestEORM(loggedSetsAfterDeletion);
            newTopLoggedSet.setTopLoggedSet(true);
            save(newTopLoggedSet);
        }

        return loggedSetsAfterDeletion;
    }

    @Override
    public List<LoggedSet> updateLoggedSet(LoggedSet toBeUpdated, LoggedSet newLoggedSet) {
        LoggedExercise loggedExercise = toBeUpdated.getLoggedExercise();
        Integer repsToEvaluate = toBeUpdated.getReps();
        Long exerciseIdToEvaluate = toBeUpdated.getLoggedExercise().getExercise().getId();
        Long userId = toBeUpdated.getLoggedExercise().getWorkout().getUser().getId();

        boolean shouldUpdateExercisePr = toBeUpdated.getExercisePr() != null;
        if (shouldUpdateExercisePr) {
            deleteExercisePr(toBeUpdated.getExercisePr());
            // Don't forget to filter out toBeUpdated set, otherwise it will be set as the pr again obviously...
            List<LoggedSet> historyLoggedSets = getByRepsAndExerciseAndUserId(repsToEvaluate, exerciseIdToEvaluate, userId)
                    .stream().filter(set -> !Objects.equals(set.getId(), toBeUpdated.getId())).toList();
            updateNewExercisePrFromOldSets(historyLoggedSets);
        }

        // update old logged set with new values
        toBeUpdated.setWeight(newLoggedSet.getWeight());
        toBeUpdated.setReps(newLoggedSet.getReps());
        toBeUpdated.setEstimatedOneRepMax(newLoggedSet.getEstimatedOneRepMax());

        updateExercisePrWhenLoggedSetIsAdded(toBeUpdated);

        List<LoggedSet> otherLoggedSets = loggedExercise.getLoggedSets().stream()
                .filter(set -> !Objects.equals(set.getId(), toBeUpdated.getId()))
                .toList();

        updateTopLoggedSetWhenLoggedSetIsAdded(toBeUpdated, otherLoggedSets);

        save(toBeUpdated);

        return loggedExercise.getLoggedSets().stream()
                .map(set -> Objects.equals(set.getId(), toBeUpdated.getId()) ? toBeUpdated : set)
                .toList();
    }


    public void updateExercisePrWhenLoggedSetIsAdded(LoggedSet toBeAddedLoggedSet) {
        Long exerciseId = toBeAddedLoggedSet.getLoggedExercise().getExercise().getId();
        Long userId = toBeAddedLoggedSet.getLoggedExercise().getWorkout().getUser().getId();

        ExercisePr currentExercisePr = exercisePrService.getByRepsAndExerciseAndUserId(toBeAddedLoggedSet.getReps(),
                exerciseId, userId);

        if (currentExercisePr == null) {
            ExercisePr newExercisePr = ExercisePr.from(toBeAddedLoggedSet);
            toBeAddedLoggedSet.setExercisePr(newExercisePr);
            return;
        }

        Double currentEORM = currentExercisePr.getEstimatedOneRepMax();
        Double requestEORM = toBeAddedLoggedSet.getEstimatedOneRepMax();
        boolean isNotANewExercisePr = Double.compare(currentEORM, requestEORM) >= 0;

        if (isNotANewExercisePr) return;

        deleteExercisePr(currentExercisePr);

        ExercisePr newExercisePr = ExercisePr.from(toBeAddedLoggedSet);
        toBeAddedLoggedSet.setExercisePr(newExercisePr);
    }


    public void updateTopLoggedSetWhenLoggedSetIsAdded(LoggedSet loggedSetToBeAdded, List<LoggedSet> previousLoggedSets) {
        if (previousLoggedSets.isEmpty()) {
            loggedSetToBeAdded.setTopLoggedSet(true);
            return;
        }

        boolean isNewTopSet = isNewTopLoggedSet(loggedSetToBeAdded, previousLoggedSets);
        if (!isNewTopSet) return;

        LoggedSet previousTopSet = getLoggedSetWithHighestEORM(previousLoggedSets);
        previousTopSet.setTopLoggedSet(false);
        save(previousTopSet);

        loggedSetToBeAdded.setTopLoggedSet(true);
    }

    public void updateNewExercisePrFromOldSets(List<LoggedSet> otherLoggedSets) {
        if (otherLoggedSets.isEmpty()) return;

        LoggedSet loggedSetToUpdate = getLoggedSetWithHighestEORM(otherLoggedSets);
        ExercisePr newExercisePr = ExercisePr.from(loggedSetToUpdate);

        loggedSetToUpdate.setExercisePr(newExercisePr);
        save(loggedSetToUpdate);
    }

    public boolean isNewTopLoggedSet(LoggedSet newLoggedSet, List<LoggedSet> previousLoggedSets) {
        double previousTopSet = previousLoggedSets.stream()
                .mapToDouble(LoggedSet::getEstimatedOneRepMax)
                .max()
                .orElse(Double.NEGATIVE_INFINITY);

        return previousTopSet < newLoggedSet.getEstimatedOneRepMax();

    }

    // what happens if two exercises have the same EORM?
    public LoggedSet getLoggedSetWithHighestEORM(@NotNull List<LoggedSet> loggedSets) {
        return loggedSets.stream().max(Comparator.comparing(LoggedSet::getEstimatedOneRepMax)).orElseThrow();
    }

    public void deleteExercisePr(ExercisePr exercisePr) {
        LoggedSet loggedSet = exercisePr.getLoggedSet();
        loggedSet.setExercisePr(null);
        save(loggedSet);
    }

}
