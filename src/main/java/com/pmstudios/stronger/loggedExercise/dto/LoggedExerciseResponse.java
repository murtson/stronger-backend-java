package com.pmstudios.stronger.loggedExercise.dto;

import com.pmstudios.stronger.exercise.dto.ExerciseResponse;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetResponse;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutStatusEnum;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.pmstudios.stronger.Constants.ISO_DATE_TIME_FORMAT;

public record LoggedExerciseResponse(Long loggedExerciseId,
                                     List<LoggedSetResponse> loggedSets,
                                     ExerciseResponse exercise,
                                     LocalWorkoutDTO workout) {

    public static LoggedExerciseResponse from(LoggedExercise loggedExercise) {

        List<LoggedSetResponse> loggedSetResponses = Optional.ofNullable(loggedExercise.getLoggedSets())
                .orElse(Collections.emptyList())
                .stream()
                .map(LoggedSetResponse::from)
                .toList();

        return new LoggedExerciseResponse(loggedExercise.getId(), loggedSetResponses,
                ExerciseResponse.from(loggedExercise.getExercise()), LocalWorkoutDTO.from(loggedExercise.getWorkout()));
    }

    record LocalWorkoutDTO(Long workoutId, String name, String startDate, String completeDate,
                           WorkoutStatusEnum workoutStatus, Long userId) {

        public static LocalWorkoutDTO from(Workout entity) {
            String formattedStartDate = entity.getStartDate().format(ISO_DATE_TIME_FORMAT);
            String formattedCompleteDate = Optional.ofNullable(entity.getCompleteDate()).map(date -> date.format(ISO_DATE_TIME_FORMAT)).orElse(null);

            return new LocalWorkoutDTO(entity.getId(), entity.getName(), formattedStartDate, formattedCompleteDate,
                    entity.getWorkoutStatus(), entity.getUser().getId());
        }
    }
}
