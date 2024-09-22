package com.pmstudios.stronger.exercise;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.LoggedSet;

import java.util.List;

public interface ExerciseService {

    Exercise getExercise(Long exerciseId);

    Exercise saveExercise(Exercise exercise, Long exerciseCategoryId);

    void deleteExercise(Long exerciseId);

    List<Exercise> getExercises();

    List<Exercise> getExerciseCategoryExercises(Long exerciseCategoryId);


}
