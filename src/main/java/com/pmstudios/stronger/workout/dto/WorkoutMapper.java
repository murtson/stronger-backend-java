package com.pmstudios.stronger.workout.dto;

import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseDto;
import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseMapper;
import com.pmstudios.stronger.workout.Workout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkoutMapper {

    @Autowired
    LoggedExerciseMapper loggedExerciseMapper;
    public WorkoutDto entityToDto(Workout entity) {

        List<LoggedExerciseDto> loggedExerciseDtos =  entity.getLoggedExercises() == null ||  entity.getLoggedExercises().isEmpty()? List.of() :entity.getLoggedExercises()
                .stream()
                .map(loggedExercise -> loggedExerciseMapper.entityToDto(loggedExercise))
                .toList();

        return new WorkoutDto(entity.getId(), entity.getName(),
                entity.getStartDate(), entity.getCompleteDate(),
                entity.getWorkoutStatus(), entity.getUser().getId(),
                loggedExerciseDtos);
    }

}
