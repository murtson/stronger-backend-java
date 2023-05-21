package com.pmstudios.stronger.loggedExercise.dto;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetResponse;
import lombok.*;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoggedExerciseResponse {


    private Long loggedExerciseId;

    private List<LoggedSetResponse> loggedSets;

    private Exercise exercise;

    private Long workoutId;

    private Long userId;

    public static LoggedExerciseResponse from(LoggedExercise loggedExercise) {
        return LoggedExerciseResponse.builder()
                .loggedExerciseId(loggedExercise.getId())
                .exercise(loggedExercise.getExercise())
                .workoutId(loggedExercise.getWorkout().getId())
                .userId(loggedExercise.getWorkout().getUser().getId())
                .loggedSets(loggedExercise.getLoggedSets().stream().map(LoggedSetResponse::from).toList())
                .build();

    }


}
