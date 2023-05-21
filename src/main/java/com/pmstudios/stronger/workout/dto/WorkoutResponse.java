package com.pmstudios.stronger.workout.dto;

import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseResponse;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutStatusEnum;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutResponse {

    private Long workoutId;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime completeDate;
    private WorkoutStatusEnum workoutStatus;
    private Long userId;
    private List<LoggedExerciseResponse> loggedExercises;

    public static WorkoutResponse from(Workout entity) {

        boolean noLoggedExercises = entity.getLoggedExercises() == null || entity.getLoggedExercises().isEmpty();

        List<LoggedExerciseResponse> loggedExerciseResponses = noLoggedExercises ?
                List.of() : entity.getLoggedExercises().stream().map(LoggedExerciseResponse::from).toList();

        return WorkoutResponse.builder()
                .workoutId(entity.getId())
                .name(entity.getName())
                .startDate(entity.getStartDate())
                .completeDate(entity.getCompleteDate())
                .workoutStatus(entity.getWorkoutStatus())
                .userId(entity.getUser().getId())
                .loggedExercises(loggedExerciseResponses)
                .build();
    }
}
