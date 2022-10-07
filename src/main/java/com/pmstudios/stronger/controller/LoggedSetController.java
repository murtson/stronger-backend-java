package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.entity.LoggedSet;
import com.pmstudios.stronger.service.LoggedSetService;
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

    @GetMapping("/{id}")
    ResponseEntity<LoggedSet> getSet(@PathVariable Long id) {
        LoggedSet loggedSet = loggedSetService.getLoggedSet(id);
        return new ResponseEntity<>(loggedSet, HttpStatus.OK);
    }

    @GetMapping("/logged-exercise/{loggedExerciseId}")
    ResponseEntity<List<LoggedSet>> getLoggedExerciseLoggedSets(@PathVariable Long workoutSetId) {
        List<LoggedSet> loggedSets = loggedSetService.getLoggedExerciseLoggedSets(workoutSetId);
        return new ResponseEntity<>(loggedSets, HttpStatus.OK);
    }

    @PostMapping("/logged-exercise/{loggedExerciseId}")
    ResponseEntity<LoggedSet> saveSet(@PathVariable Long loggedExerciseId, @RequestBody LoggedSet loggedSet) {
        LoggedSet createdLoggedSet = loggedSetService.saveLoggedSet(loggedSet, loggedExerciseId);
        return new ResponseEntity<>(createdLoggedSet, HttpStatus.CREATED);
    }

}
