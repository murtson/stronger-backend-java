package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.entity.Workout;
import com.pmstudios.stronger.service.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/workout")
public class WorkoutController {

    WorkoutService workoutService;

    @PostMapping("/user/{userId}")
    ResponseEntity<Workout> saveWorkout(@RequestBody Workout workout, @PathVariable Long userId) {
        Workout savedWorkout = workoutService.saveWorkout(workout, userId);
        return new ResponseEntity<>(savedWorkout, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Workout> getWorkout(@PathVariable Long id) {
        Workout workout = workoutService.getWorkout(id);
        return new ResponseEntity<>(workout, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<Workout>> getWorkouts() {
        List<Workout> workouts = workoutService.getWorkouts();
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
