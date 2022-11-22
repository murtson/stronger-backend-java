package com.pmstudios.stronger.loggedExercise;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.loggedSet.UpdateLoggedSetDTO;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exercise.ExerciseService;
import com.pmstudios.stronger.loggedSet.LoggedSetService;
import com.pmstudios.stronger.workout.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

@AllArgsConstructor
@Service
public class LoggedExerciseServiceImpl implements LoggedExerciseService {

    LoggedExerciseRepository loggedExerciseRepository;
    WorkoutService workoutService;
    ExerciseService exerciseService;
    LoggedSetService loggedSetService;

    @Override
    public LoggedExercise getLoggedExercise(Long id) {
         return loggedExerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, LoggedExercise.class));
    }

    @Override
    public LoggedExercise saveLoggedExercise(LoggedExercise loggedExercise, Long workoutId, Long exerciseId) {
        Workout workout = workoutService.getWorkout(workoutId);
        Exercise exercise = exerciseService.getExercise(exerciseId);
        loggedExercise.setWorkout(workout);
        loggedExercise.setExercise(exercise);
        return loggedExerciseRepository.save(loggedExercise);
    }

    @Override
    public LoggedExercise updateLoggedExerciseSets(Long loggedExerciseId, List<UpdateLoggedSetDTO> loggedSets) {
        LoggedExercise loggedExercise = this.getLoggedExercise(loggedExerciseId);
        List<LoggedSet> updatedSets = loggedSetService.updateLoggedSets(loggedSets, loggedExercise);
        BigDecimal exerciseTopSet = getTopSet(updatedSets);
        loggedExercise.setLoggedSets(updatedSets);
        loggedExercise.setExerciseTopSet(exerciseTopSet);
        return loggedExercise;
    }

    @Override
    public void deleteLoggedExercise(Long id) {
        loggedExerciseRepository.deleteById(id);
    }

    @Override
    public List<LoggedExercise> getWorkoutLoggedExercises(Long workoutId) {
        return loggedExerciseRepository.findByWorkoutId(workoutId);
    }


    private BigDecimal getTopSet(List<LoggedSet> loggedSets) {
        return (loggedSets.isEmpty()) ? null : loggedSets
                .stream()
                .max(Comparator.comparing(LoggedSet::getEstimatedOneRepMax))
                .get().getEstimatedOneRepMax();
    }


}
