package com.pmstudios.stronger.service;

import com.pmstudios.stronger.pojo.Exercise;

import java.util.List;

public interface ExerciseService {


    Exercise getExercise(int id);

    List<Exercise> getExercises();
    void saveExercise(Exercise exercise);



}
