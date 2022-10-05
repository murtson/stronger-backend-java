package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.entity.UserWorkout;
import com.pmstudios.stronger.service.UserWorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/user-workout")
public class UserWorkoutController {

    UserWorkoutService userWorkoutService;

    @PostMapping()
    ResponseEntity<UserWorkout> saveUserWorkout(@RequestBody UserWorkout workout) {
        UserWorkout savedWorkout = userWorkoutService.saveUserWorkout(workout);
        return new ResponseEntity<>(savedWorkout, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<UserWorkout> getUserWorkout(@PathVariable Long id) {
        UserWorkout userWorkout = userWorkoutService.getUserWorkout(id);
        return new ResponseEntity<>(userWorkout, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<UserWorkout>> getUserWorkouts() {
        List<UserWorkout> userWorkouts = userWorkoutService.getUserWorkouts();
        return new ResponseEntity<>(userWorkouts, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteUserWorkout(@PathVariable Long id) {
        userWorkoutService.deleteUserWorkout(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
