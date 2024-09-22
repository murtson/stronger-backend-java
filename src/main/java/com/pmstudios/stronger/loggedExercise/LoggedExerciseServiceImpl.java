package com.pmstudios.stronger.loggedExercise;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exercise.ExerciseService;
import com.pmstudios.stronger.workout.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LoggedExerciseServiceImpl implements LoggedExerciseService {

    LoggedExerciseRepository loggedExerciseRepository;
    WorkoutService workoutService;
    ExerciseService exerciseService;

    @Override
    public LoggedExercise getById(Long id) {
        return loggedExerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, LoggedExercise.class));
    }

    @Override
    public LoggedExercise create(Workout workout, Long exerciseId) {
        Exercise exercise = exerciseService.getExercise(exerciseId);
        LoggedExercise loggedExercise = new LoggedExercise(workout, exercise);
        return loggedExerciseRepository.save(loggedExercise);
    }

    @Override
    public LoggedExercise save(LoggedExercise loggedExercise) {
        return loggedExerciseRepository.save(loggedExercise);
    }

    @Override
    public void deleteById(Long id) {
        loggedExerciseRepository.deleteById(id);
    }

    @Override
    public void delete(LoggedExercise loggedExercise) {
        loggedExerciseRepository.delete(loggedExercise);
    }

    @Override
    public List<LoggedExercise> getByWorkoutId(Long workoutId) {
        return loggedExerciseRepository.findByWorkoutId(workoutId);
    }

    @Override
    public List<LoggedExercise> getByExerciseIdAndUserId(Long exerciseId, Long userId) {
        return loggedExerciseRepository.findByExerciseIdAndWorkout_User_Id(exerciseId, userId);
    }

    @Override
    public List<LoggedExercise> getLoggedExerciseByExercise(Long exerciseId, Long userId) {
       return loggedExerciseRepository.findByExerciseIdAndWorkout_User_Id(exerciseId, userId);
    }


}
