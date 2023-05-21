package com.pmstudios.stronger.loggedExercise;

import com.pmstudios.stronger.workout.Workout;

import java.util.List;

public interface LoggedExerciseService {

    LoggedExercise getById(Long id);

    LoggedExercise create(Workout workout, Long exerciseId);

    LoggedExercise save(LoggedExercise loggedExercise);

    void deleteById(Long id);

    void delete(LoggedExercise loggedExercise);

    List<LoggedExercise> getByWorkoutId(Long workoutId);

    List<LoggedExercise> getByExerciseIdAndUserId(Long exerciseId, Long userId);

}
