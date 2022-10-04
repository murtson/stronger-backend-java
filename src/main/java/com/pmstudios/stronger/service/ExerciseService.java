package com.pmstudios.stronger.service;

import com.pmstudios.stronger.pojo.Exercise;
import com.pmstudios.stronger.pojo.ExerciseCategory;

import java.util.List;

public interface ExerciseService {

    Exercise getExercise(Long id);

    Exercise saveExercise(Exercise exercise);

    void deleteExercise(Long id);

    List<Exercise> getExercises();


}
