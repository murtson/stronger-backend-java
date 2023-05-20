package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedSet.dto.LoggedSetDto;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetMapper;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetUpdateDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logged-set")
public class LoggedSetController {

    @Autowired
    LoggedSetService loggedSetService;
    @Autowired
    LoggedSetMapper mapper;

    @PostMapping("/logged-exercise/{loggedExerciseId}")
    ResponseEntity<List<LoggedSetDto>> addLoggedSet(
            @PathVariable Long loggedExerciseId,
            @RequestBody LoggedSetUpdateDto request
    ) {
        LoggedSet loggedSet = mapper.dtoToEntity(request);
        List<LoggedSet> savedLoggedSet = loggedSetService.addLoggedSet(loggedExerciseId, loggedSet);
        List<LoggedSetDto> loggedSetDtos = savedLoggedSet.stream().map(set -> mapper.entityToDto(set)).toList();
        return new ResponseEntity<>(loggedSetDtos, HttpStatus.CREATED);

    }

    @DeleteMapping("/{loggedSetId}")
    ResponseEntity<List<LoggedSetDto>> deleteLoggedSet(@PathVariable Long loggedSetId) {
        List<LoggedSet> savedLoggedSet = loggedSetService.removeLoggedSet(loggedSetId);
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
