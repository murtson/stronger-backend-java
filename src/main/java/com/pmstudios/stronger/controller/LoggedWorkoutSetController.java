package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.entity.LoggedWorkoutSet;
import com.pmstudios.stronger.service.LoggedWorkoutSetService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/logged-workout-set")
public class LoggedWorkoutSetController {

    LoggedWorkoutSetService loggedWorkoutSetService;

    @PostMapping("/user-workout/${workoutId}")
    ResponseEntity<LoggedWorkoutSet> createLoggedWorkoutSet(@PathVariable Long workoutId) {
        loggedWorkoutSetService.saveLoggedWorkoutSet()
    }


}
