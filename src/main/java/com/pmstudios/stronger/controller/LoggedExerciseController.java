package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.entity.LoggedExercise;
import com.pmstudios.stronger.entity.LoggedSet;
import com.pmstudios.stronger.service.LoggedExerciseService;
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

    @PostMapping("/workout/{workoutId}/exercise/{exerciseId}")
    ResponseEntity<LoggedExercise> createLoggedExercise(@Valid @RequestBody LoggedExercise loggedExercise, @PathVariable Long workoutId, @PathVariable Long exerciseId) {
        LoggedExercise createdLoggedExercise = loggedExerciseService.saveLoggedExercise(loggedExercise, workoutId, exerciseId);
        return new ResponseEntity<>(createdLoggedExercise, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<LoggedExercise> getLoggedExercise(@PathVariable Long id) {
        LoggedExercise loggedExercise = loggedExerciseService.getLoggedExercise(id);
        return new ResponseEntity<>(loggedExercise, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<LoggedExercise> deleteLoggedExercise(@PathVariable Long id) {
        loggedExerciseService.deleteLoggedExercise(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    ResponseEntity<LoggedExercise> updatedLoggedExerciseSets( @PathVariable Long id, @Valid @RequestBody List<LoggedSet> updatedSets) {
        LoggedExercise updatedLoggedExercise = loggedExerciseService.updateLoggedExerciseSets(id, updatedSets);
        return new ResponseEntity<>(updatedLoggedExercise, HttpStatus.OK);
    }

    @GetMapping("/workout/{workoutId}")
    ResponseEntity<List<LoggedExercise>> getWorkoutLoggedExercises(@PathVariable Long workoutId) {
        List<LoggedExercise> loggedExercises = loggedExerciseService.getWorkoutLoggedExercises(workoutId);
        return new ResponseEntity<>(loggedExercises, HttpStatus.OK);
    }



}
