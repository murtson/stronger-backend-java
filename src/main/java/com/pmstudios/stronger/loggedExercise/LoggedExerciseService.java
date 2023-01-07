package com.pmstudios.stronger.loggedExercise;

import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseDto;

import java.util.List;

public interface LoggedExerciseService {

    LoggedExercise getLoggedExercise(Long id);

    LoggedExercise saveLoggedExercise(LoggedExercise loggedExercise, Long workoutId, Long exerciseId);

    LoggedExercise save(LoggedExercise loggedExercise);

    void deleteLoggedExercise(Long id);

    List<LoggedExercise> getLoggedExercisesByWorkoutId(Long workoutId);

    List<LoggedExercise> getLoggedExercisesByExerciseIdAndUserId(Long exerciseId, Long userId);

}
