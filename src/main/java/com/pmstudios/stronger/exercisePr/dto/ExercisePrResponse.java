package com.pmstudios.stronger.exercisePr.dto;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExercisePrResponse {
    private Long id;
    private Double weight;
    private Integer reps;
    private Double estimatedOneRepMax;
    private Long userId;
    private Long workoutId;
    private Long loggedExerciseId;
    private Long loggedSetId;
    private Exercise exercise;

    public static ExercisePrResponse from(ExercisePr exercisePr) {
        if (exercisePr == null) return null;

        return ExercisePrResponse.builder()
                .id(exercisePr.getId())
                .weight(exercisePr.getWeight())
                .reps(exercisePr.getReps())
                .estimatedOneRepMax(exercisePr.getEstimatedOneRepMax())
                .userId(exercisePr.getUser().getId())
                .exercise(exercisePr.getExercise())
                .workoutId(exercisePr.getLoggedSet().getLoggedExercise().getWorkout().getId())
                .loggedSetId(exercisePr.getLoggedSet().getId())
                .loggedExerciseId(exercisePr.getLoggedSet().getLoggedExercise().getId())
                .build();
    }
}
