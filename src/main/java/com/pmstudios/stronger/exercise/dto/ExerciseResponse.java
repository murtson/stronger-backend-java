package com.pmstudios.stronger.exercise.dto;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.exerciseCategory.MuscleCategory;


public record ExerciseResponse(Long exerciseId, String exerciseName, ExerciseCategory exerciseCategory) {

    public static ExerciseResponse from(Exercise exercise) {
        ExerciseCategory category = ExerciseCategory.from(exercise);
        return new ExerciseResponse(exercise.getId(), exercise.getName(), category);
    }

    public record ExerciseCategory(Long exerciseCategoryId, MuscleCategory muscleCategory) {
        public static ExerciseCategory from(Exercise exercise) {
            return new ExerciseCategory(exercise.getExerciseCategory().getId(), exercise.getExerciseCategory().getName());
        }
    }
}
