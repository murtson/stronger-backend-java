package com.pmstudios.stronger.workout.dto;

import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseResponse;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.workout.Workout;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkoutMapper {

    public Workout createWorkoutRequestToEntity(CreateWorkoutRequest request, User user) {
        // TODO: perhaps make check here to see if startDate is > Date.now, status must be planned.
        return new Workout(request.getName(), request.getStartDate(), request.getWorkoutStatus(), user);
    }

}
