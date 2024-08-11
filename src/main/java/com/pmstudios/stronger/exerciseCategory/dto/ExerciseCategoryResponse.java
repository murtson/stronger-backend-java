package com.pmstudios.stronger.exerciseCategory.dto;

import com.pmstudios.stronger.exercise.dto.ExerciseResponse;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategory;
import com.pmstudios.stronger.exerciseCategory.MuscleCategory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public record ExerciseCategoryResponse(Long exerciseCategoryId, MuscleCategory muscleCategory, List<ExerciseResponse> exercises) {

    public static ExerciseCategoryResponse from(ExerciseCategory entity) {

        List<ExerciseResponse> exercises = Optional.ofNullable(entity.getExercises())
                .orElse(Collections.emptyList())
                .stream()
                .map(ExerciseResponse::from)
                .toList();

        return new ExerciseCategoryResponse(entity.getId(), entity.getName(), exercises);
    }

}
