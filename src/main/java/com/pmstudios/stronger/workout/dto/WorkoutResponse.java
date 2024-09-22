package com.pmstudios.stronger.workout.dto;

import com.pmstudios.stronger.exercise.dto.ExerciseResponse;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetResponse;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutStatusEnum;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.pmstudios.stronger.Constants.ISO_DATE_TIME_FORMAT;


public record WorkoutResponse(Long workoutId,
                              String name,
                              String startDate,
                              String completeDate,
                              WorkoutStatusEnum workoutStatus,
                              Long userId,
                              List<LocalLoggedExercise> loggedExercises) {

    public static WorkoutResponse from(Workout entity) {

        List<LocalLoggedExercise> loggedExerciseResponses = Optional.ofNullable(entity.getLoggedExercises())
                .orElse(Collections.emptyList())
                .stream()
                .map(LocalLoggedExercise::from)
                .collect(Collectors.toList());

        String formattedStartDate = entity.getStartDate().format(ISO_DATE_TIME_FORMAT);
        String formattedCompleteDate = Optional.ofNullable(entity.getCompleteDate())
                .map(date -> date.format(ISO_DATE_TIME_FORMAT))
                .orElse(null);

        return new WorkoutResponse(entity.getId(), entity.getName(), formattedStartDate, formattedCompleteDate,
                entity.getWorkoutStatus(), entity.getUser().getId(), loggedExerciseResponses);

    }

    record LocalLoggedExercise(Long loggedExerciseId,
                               List<LoggedSetResponse> loggedSets,
                               ExerciseResponse exercise) {

        public static LocalLoggedExercise from(LoggedExercise loggedExercise) {

            List<LoggedSetResponse> loggedSetResponses = Optional.ofNullable(loggedExercise.getLoggedSets())
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(LoggedSetResponse::from)
                    .toList();

            return new LocalLoggedExercise(loggedExercise.getId(), loggedSetResponses, ExerciseResponse.from(loggedExercise.getExercise()));
        }
    }
}
