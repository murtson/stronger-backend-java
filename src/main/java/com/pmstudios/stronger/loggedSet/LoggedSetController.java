package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedSet.dto.LoggedSetDto;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetMapper;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/logged-set")
public class LoggedSetController {

    LoggedSetService loggedSetService;
    LoggedSetMapper mapper;

    @PostMapping("/logged-exercise/{loggedExerciseId}")
    ResponseEntity<List<LoggedSetDto>> createLoggedSet(@PathVariable Long loggedExerciseId,
                                                 @RequestBody LoggedSetUpdateDto request) {

        LoggedSet loggedSet = mapper.dtoToEntity(request);

        List<LoggedSet> savedLoggedSet = loggedSetService.createLoggedSet(loggedExerciseId, loggedSet);

        List<LoggedSetDto> loggedSetDtos = savedLoggedSet.stream().map(set -> mapper.entityToDto(set)).toList();

        return new ResponseEntity<>(loggedSetDtos, HttpStatus.CREATED);

    }

    @GetMapping("/exercise/{exerciseId}/reps/{repsAmount}")
    ResponseEntity<List<LoggedSetDto>> getLoggedSetsByRepsAndExercise(@PathVariable int repsAmount, @PathVariable Long exerciseId) {

        List<LoggedSet> loggedSets = loggedSetService.getByRepsAndExerciseAndUserId(repsAmount, exerciseId, 1L);
        List<LoggedSetDto> loggedSetsDto = loggedSets.stream().map(set -> mapper.entityToDto(set)).toList();
        return new ResponseEntity<>(loggedSetsDto, HttpStatus.OK);

    }

}
