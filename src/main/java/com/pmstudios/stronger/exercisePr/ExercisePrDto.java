package com.pmstudios.stronger.exercisePr;

import com.pmstudios.stronger.exercise.Exercise;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class ExercisePrDto {

    @NonNull
    private Long id;

    @NonNull
    private Double weight;

    @NonNull
    private Integer reps;

    @NonNull
    private Double estimatedOneRepMax;

    @NonNull
    private Long userId;

    @NonNull
    private Long workoutId;

    @NonNull
    private Long loggedExerciseId;

    @NonNull
    private Long loggedSetId;

    @NonNull
    private Exercise exercise;

}
