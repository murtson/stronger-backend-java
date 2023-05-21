package com.pmstudios.stronger.workout;

import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import com.pmstudios.stronger.user.dto.UserUtils;
import com.pmstudios.stronger.workout.dto.CreateWorkoutRequest;
import com.pmstudios.stronger.workout.dto.WorkoutResponse;
import com.pmstudios.stronger.workout.dto.WorkoutMapper;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/workout")
public class WorkoutController {

    UserService userService;
    WorkoutService workoutService;
    WorkoutMapper mapper;

    @GetMapping("/{workoutId}")
    ResponseEntity<WorkoutResponse> getWorkout(@PathVariable Long workoutId) {
        Workout workout = workoutService.getWorkoutById(workoutId);
        WorkoutResponse workoutResponse = WorkoutResponse.from(workout);
        return new ResponseEntity<>(workoutResponse, HttpStatus.OK);
    }

    @PostMapping("/create")
    ResponseEntity<WorkoutResponse> createWorkout(
            @Validated @RequestBody @Valid CreateWorkoutRequest request, @AuthenticationPrincipal User authUser) {
        User user = userService.getUserById(authUser.getId());

        Workout workout = mapper.createWorkoutRequestToEntity(request, user);
        Workout createdWorkout = workoutService.saveWorkout(workout);
        WorkoutResponse createdWorkoutResponse = WorkoutResponse.from(createdWorkout);

        return new ResponseEntity<>(createdWorkoutResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{workoutId}")
    ResponseEntity<?> deleteWorkout(@PathVariable Long workoutId, @AuthenticationPrincipal User authUser) {
        Workout workoutToBeDeleted = workoutService.getWorkoutById(workoutId);

        if (!Objects.equals(workoutToBeDeleted.getUser().getId(), authUser.getId()) && !UserUtils.isAdminUser(authUser)) {
            String message = "You are not allowed to delete other user's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        workoutService.deleteWorkoutById(workoutId);
        String message = "You deleted workout with workoutId: " + workoutId;
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/{id}")
    ResponseEntity<Workout> completeWorkout(@PathVariable Long id) {
        Workout completedWorkout = workoutService.completeWorkout(id);
        return new ResponseEntity<>(completedWorkout, HttpStatus.OK);
    }

    @GetMapping("/user/all")
    ResponseEntity<List<Workout>> getUserWorkouts(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime toDate,
            @AuthenticationPrincipal User authUser
    ) {
        List<Workout> userWorkouts;
        if (fromDate == null || toDate == null) {
            userWorkouts = workoutService.getWorkoutByUserId(authUser.getId());
        } else {
            userWorkouts = workoutService.getUserWorkoutsBetweenDates(fromDate, toDate, authUser.getId());
        }
        return new ResponseEntity<>(userWorkouts, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<Workout>> getWorkouts() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }


}
