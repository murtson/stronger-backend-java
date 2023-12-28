package com.pmstudios.stronger.loggedExercise.dto;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetResponse;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoggedExerciseResponse {


    private Long loggedExerciseId;

    private List<LoggedSetResponse> loggedSets;

    private Exercise exercise;

    public static LoggedExerciseResponse from(LoggedExercise loggedExercise) {

        List<LoggedSetResponse> loggedSetResponses = Optional.ofNullable(loggedExercise.getLoggedSets())
                .orElse(Collections.emptyList())
                .stream()
                .map(LoggedSetResponse::from)
                .toList();

        return LoggedExerciseResponse.builder()
                .loggedExerciseId(loggedExercise.getId())
                .exercise(loggedExercise.getExercise())
                .loggedSets(loggedSetResponses)
                .build();

    }


}
