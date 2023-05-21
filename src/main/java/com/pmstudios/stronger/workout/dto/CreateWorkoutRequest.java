package com.pmstudios.stronger.workout.dto;


import com.pmstudios.stronger.workout.WorkoutStatusEnum;
import lombok.*;


import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Getter
@Setter
public class CreateWorkoutRequest {
    @NotNull(message = "name is required")
    private String name;
    @NotNull(message = "startDate is required")
    private LocalDateTime startDate;
    @NotNull(message = "workoutStatus is required")
    private WorkoutStatusEnum workoutStatus;
}
