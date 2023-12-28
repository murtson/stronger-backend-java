package com.pmstudios.stronger.workout.dto;

import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseResponse;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutStatusEnum;
import lombok.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pmstudios.stronger.Constants.ISO_DATE_TIME_FORMAT;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutResponse {

    private Long workoutId;
    private String name;
    private String startDate;
    private String completeDate;
    private WorkoutStatusEnum workoutStatus;
    private Long userId;
    private List<LoggedExerciseResponse> loggedExercises;

    public static WorkoutResponse from(Workout entity) {

        List<LoggedExerciseResponse> loggedExerciseResponses = Optional.ofNullable(entity.getLoggedExercises())
                .orElse(Collections.emptyList())
                .stream()
                .map(LoggedExerciseResponse::from)
                .collect(Collectors.toList());

        String formattedStartDate = entity.getStartDate().format(ISO_DATE_TIME_FORMAT);
        String formattedCompleteDate = Optional.ofNullable(entity.getCompleteDate())
                .map(date -> date.format(ISO_DATE_TIME_FORMAT))
                .orElse(null);

        return WorkoutResponse.builder()
                .workoutId(entity.getId())
                .name(entity.getName())
                .startDate(formattedStartDate)
                .completeDate(formattedCompleteDate)
                .workoutStatus(entity.getWorkoutStatus())
                .userId(entity.getUser().getId())
                .loggedExercises(loggedExerciseResponses)
                .build();
    }
}
