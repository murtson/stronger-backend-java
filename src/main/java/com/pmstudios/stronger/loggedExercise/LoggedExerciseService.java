package com.pmstudios.stronger.loggedExercise;

import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.loggedSet.UpdateLoggedSetDTO;

import java.util.List;

public interface LoggedExerciseService {

    LoggedExercise getLoggedExercise(Long id);

    LoggedExercise saveLoggedExercise(LoggedExercise loggedExercise, Long workoutId, Long exerciseId);

    LoggedExercise updateLoggedExerciseSets(Long loggedExerciseId, List<UpdateLoggedSetDTO> updatedSets);

    void deleteLoggedExercise(Long id);

    List<LoggedExercise> getWorkoutLoggedExercises(Long workoutId);

}
