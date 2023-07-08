package com.pmstudios.stronger.workout;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import com.pmstudios.stronger.user.dto.UserUtils;
import com.pmstudios.stronger.workout.dto.CreateWorkoutRequest;
import com.pmstudios.stronger.workout.dto.WorkoutResponse;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        Workout workout = CreateWorkoutRequest.toEntity(request, user);
        Workout createdWorkout = workoutService.saveWorkout(workout);
        WorkoutResponse createdWorkoutResponse = WorkoutResponse.from(createdWorkout);

        return new ResponseEntity<>(createdWorkoutResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{workoutId}")
    ResponseEntity<?> deleteWorkout(@PathVariable Long workoutId, @AuthenticationPrincipal User authUser) {
        Workout workoutToBeDeleted = workoutService.getWorkoutById(workoutId);

        if (!isModifyingOwnData(workoutToBeDeleted, authUser) && !UserUtils.isAdminUser(authUser)) {
            String message = "You are not allowed to delete other user's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        workoutService.deleteWorkoutById(workoutId);
        String message = "You deleted workout with workoutId: " + workoutId;
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }

    @PostMapping("/{workoutId}")
    ResponseEntity<?> completeWorkout(@PathVariable Long workoutId, @AuthenticationPrincipal User authUser) {
        Workout workoutToComplete = workoutService.getWorkoutById(workoutId);

        if (!isModifyingOwnData(workoutToComplete, authUser) && !UserUtils.isAdminUser(authUser)) {
            String message = "You cannot complete someone else's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        Workout completedWorkout = workoutService.completeWorkout(workoutId);
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

    private boolean isModifyingOwnData(Workout workout, User authUser) {
        return Objects.equals(workout.getUser().getId(), authUser.getId());
    }


}
