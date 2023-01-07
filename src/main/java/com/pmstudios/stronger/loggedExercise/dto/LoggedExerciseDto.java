package com.pmstudios.stronger.loggedExercise.dto;
import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetDto;
import com.pmstudios.stronger.workout.Workout;
import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class LoggedExerciseDto {

    @NonNull
    private Long id;

    @NonNull
    private List<LoggedSetDto> loggedSets;

    @NonNull
    private Exercise exercise;

    @NonNull
    private Long workoutId;

    @NonNull
    private Long userId;

}
