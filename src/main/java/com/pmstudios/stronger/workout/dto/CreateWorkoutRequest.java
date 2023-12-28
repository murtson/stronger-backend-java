package com.pmstudios.stronger.workout.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.utilities.LocalDateTimeDeserializer;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutStatusEnum;
import lombok.*;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class CreateWorkoutRequest {

    @NotNull(message = "startDate is required")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime startDate;

    @NotNull(message = "workoutStatus is required")
    private WorkoutStatusEnum WorkoutStatus;

    public static Workout toEntity(CreateWorkoutRequest request, User user) {
        return new Workout("Unnamed workout", request.getStartDate(), request.getWorkoutStatus(), user);
    }

}
