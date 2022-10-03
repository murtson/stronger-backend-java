package com.pmstudios.stronger.pojo;


import java.util.ArrayList;
import java.util.List;

public class ExerciseCategory {
    public enum MuscleCategory {
        CHEST,
        TRICEPS,
        SHOULDERS,
        BACK,
        BICEPS,
        LEGS,
        ABS
    }

    private int id;
    private final MuscleCategory category;
    private final List<Exercise> exercises;

    public ExerciseCategory(int id, MuscleCategory category, List<Exercise> exercises) {
        this.id = id;
        this.category = category;
        this.exercises = exercises;
    }

    public ExerciseCategory(int id, MuscleCategory category) {
        this.id = id;
        this.category = category;
        this.exercises = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(new Exercise(exercise));
    }

}
