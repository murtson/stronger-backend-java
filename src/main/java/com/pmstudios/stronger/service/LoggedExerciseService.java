package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.LoggedExercise;
import com.pmstudios.stronger.entity.LoggedSet;

import java.util.List;

public interface LoggedExerciseService {

    LoggedExercise getLoggedExercise(Long id);

    LoggedExercise saveLoggedExercise(LoggedExercise loggedExercise, Long workoutId, Long exerciseId);

    LoggedExercise updateLoggedExerciseSets(Long loggedExerciseId, List<LoggedSet> updatedSets);

    void deleteLoggedExercise(Long id);

    List<LoggedExercise> getWorkoutLoggedExercises(Long workoutId);

}
