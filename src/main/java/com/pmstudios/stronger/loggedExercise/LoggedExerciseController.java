package com.pmstudios.stronger.loggedExercise;

import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseDto;
import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseMapper;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/logged-exercise")
public class LoggedExerciseController {

    LoggedExerciseService loggedExerciseService;
    LoggedExerciseMapper mapper;

    @PostMapping("/workout/{workoutId}/exercise/{exerciseId}")
    ResponseEntity<LoggedExercise> createLoggedExercise(
            @Valid @RequestBody LoggedExercise loggedExercise,
            @PathVariable Long workoutId,
            @PathVariable Long exerciseId
    ) {
        // TODO: fix so that loggedSetsDTO can be sent with the req
        LoggedExercise createdLoggedExercise = loggedExerciseService.saveLoggedExercise(loggedExercise, workoutId, exerciseId);
        return new ResponseEntity<>(createdLoggedExercise, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<LoggedExerciseDto> getLoggedExercise(@PathVariable Long id) {
        LoggedExercise loggedExercise = loggedExerciseService.getLoggedExercise(id);
//        if(loggedExercise.getLoggedSets().isEmpty()) return deleteLoggedExercise(id);
        return new ResponseEntity<>(mapper.entityToDto(loggedExercise), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<LoggedExercise> deleteLoggedExercise(@PathVariable Long id) {
        loggedExerciseService.deleteLoggedExercise(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/workout/{workoutId}")
    ResponseEntity<List<LoggedExerciseDto>> getLoggedExercisesByWorkoutId(@PathVariable Long workoutId) {
        List<LoggedExercise> loggedExercises = loggedExerciseService.getLoggedExercisesByWorkoutId(workoutId);
        List<LoggedExerciseDto> loggedExerciseDtos = loggedExercises
                .stream()
                .map(exercise -> mapper.entityToDto(exercise))
                .toList();

        return new ResponseEntity<>(loggedExerciseDtos, HttpStatus.OK);
    }

    @GetMapping("/exercise/{exerciseId}/user/{userId}")
    ResponseEntity<List<LoggedExercise>> getLoggedExercisesByExerciseIdAndUserId(@PathVariable Long exerciseId, @PathVariable Long userId) {
        List<LoggedExercise> loggedExercises = loggedExerciseService.getLoggedExercisesByExerciseIdAndUserId(exerciseId, userId);
        return new ResponseEntity<>(loggedExercises, HttpStatus.OK);
    }

}
