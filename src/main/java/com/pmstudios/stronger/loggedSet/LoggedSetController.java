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

    @PutMapping("/logged-exercise/{loggedExerciseId}")
    ResponseEntity<List<LoggedSetDto>> updatedLoggedExerciseSets(@PathVariable Long loggedExerciseId,
                                                              @RequestBody List<LoggedSetUpdateDto> loggedSetUpdateDto) {

        List<LoggedSet> loggedSets = loggedSetUpdateDto.stream().map(set -> mapper.dtoToEntity(set)).toList();

        List<LoggedSet> updatedLoggedSets = loggedSetService.updateLoggedSets(loggedExerciseId, loggedSets);

        List<LoggedSetDto> updatedLoggedSetsDto = updatedLoggedSets.stream().map(set -> mapper.entityToDto(set)).toList();

        return new ResponseEntity<>(updatedLoggedSetsDto, HttpStatus.OK);
    }

    @GetMapping("/exercise/{exerciseId}/reps/{repsAmount}")
    ResponseEntity<List<LoggedSetDto>> getLoggedSetsByRepsAndExercise(@PathVariable int repsAmount, @PathVariable Long exerciseId) {

        List<LoggedSet> loggedSets = loggedSetService.getByRepsAndExerciseAndUserId(repsAmount, exerciseId, 1L);
        List<LoggedSetDto> loggedSetsDto = loggedSets.stream().map(set -> mapper.entityToDto(set)).toList();
        return new ResponseEntity<>(loggedSetsDto, HttpStatus.OK);

    }

}
