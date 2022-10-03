package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.pojo.Exercise;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ExerciseRepository {

    private final List<Exercise> exercises = new ArrayList<>();

    public Exercise getExercise(int index) { return exercises.get(index); }

    public void saveExercise(Exercise exercise) { exercises.add(exercise); }

    public List<Exercise> getExercises() { return exercises; }
}
