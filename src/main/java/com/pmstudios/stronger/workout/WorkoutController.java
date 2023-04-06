package com.pmstudios.stronger.workout;

import com.pmstudios.stronger.workout.dto.WorkoutDto;
import com.pmstudios.stronger.workout.dto.WorkoutMapper;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/workout")
public class WorkoutController {

    WorkoutService workoutService;
    WorkoutMapper mapper;

    @GetMapping("/{id}")
    ResponseEntity<WorkoutDto> getWorkout(@PathVariable Long id) {
        Workout workout = workoutService.getWorkout(id);
        WorkoutDto workoutDto = mapper.entityToDto(workout);
        return new ResponseEntity<>(workoutDto, HttpStatus.OK);
    }

    @PostMapping("/user/{userId}")
    ResponseEntity<WorkoutDto> createWorkout(@RequestBody @Valid Workout workout, @PathVariable Long userId) {
        Workout createdWorkout = workoutService.createWorkout(workout, userId);
        WorkoutDto createdWorkoutDto = mapper.entityToDto(createdWorkout);
        return new ResponseEntity<>(createdWorkoutDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}")
    ResponseEntity<Workout> completeWorkout(@PathVariable Long id) {
        Workout completedWorkout = workoutService.completeWorkout(id);
        return new ResponseEntity<>(completedWorkout, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<List<Workout>> getUserWorkouts(
            @PathVariable Long userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate
    ) {
        List<Workout> userWorkouts;
        if(fromDate == null || toDate == null) userWorkouts = workoutService.getUserWorkouts(userId);
        else userWorkouts = workoutService.getUserWorkoutsBetweenDates(fromDate, toDate, userId);
        return new ResponseEntity<>(userWorkouts, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<Workout>> getWorkouts() {
        List<Workout> workouts = workoutService.getWorkouts();
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }



}
