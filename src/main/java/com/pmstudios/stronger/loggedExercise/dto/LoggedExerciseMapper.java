package com.pmstudios.stronger.loggedExercise.dto;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetDto;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class LoggedExerciseMapper {
    @Autowired
    LoggedSetMapper loggedSetMapper;
    public LoggedExerciseDto entityToDto(LoggedExercise entity) {

        List<LoggedSetDto> loggedSetDtos = entity.getLoggedSets().isEmpty() ? List.of() : entity.getLoggedSets().stream()
                .map(loggedSet -> loggedSetMapper.entityToDto(loggedSet))
                .toList();

        return new LoggedExerciseDto(entity.getId(), loggedSetDtos, entity.getExercise(), entity.getWorkout().getId(), entity.getWorkout().getUser().getId());
    }
}
