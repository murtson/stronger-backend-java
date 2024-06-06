package com.pmstudios.stronger.workout;


import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import com.pmstudios.stronger.auth.dto.UserUtils;
import com.pmstudios.stronger.workout.dto.CreateWorkoutRequest;
import com.pmstudios.stronger.workout.dto.WorkoutResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/workout")
@RequiredArgsConstructor
public class WorkoutController {

    private final UserService userService;
    private final WorkoutService workoutService;
    Logger logger = LoggerFactory.getLogger(WorkoutController.class);

    @GetMapping("/{workoutId}")
    ResponseEntity<WorkoutResponse> getWorkout(@PathVariable Long workoutId) {
        Workout workout = workoutService.getWorkoutById(workoutId);
        WorkoutResponse workoutResponse = WorkoutResponse.from(workout);
        return ResponseEntity.ok(workoutResponse);
    }

    @GetMapping("/date/{date}")
    ResponseEntity<WorkoutResponse> getWorkoutByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @AuthenticationPrincipal User authUser) {

        return workoutService.getWorkoutByDateAndUserId(date, authUser.getId())
                .map(workout -> ResponseEntity.ok(WorkoutResponse.from(workout)))
                .orElseGet(() -> ResponseEntity.noContent().build());
    }


    @PostMapping("/create")
    ResponseEntity<?> createWorkout(
            @Validated @RequestBody @Valid CreateWorkoutRequest request,
            @AuthenticationPrincipal User authUser) {

        User user = userService.getUserById(authUser.getId());
        Workout workout = CreateWorkoutRequest.toEntity(request, user);
        Workout createdWorkout = workoutService.createWorkout(workout, request.getStartDate(), user.getId());

        WorkoutResponse response = WorkoutResponse.from(createdWorkout);
        logger.info("User '{}' started a new workout", authUser.getUsername());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{workoutId}")
    ResponseEntity<?> deleteWorkout(@PathVariable Long workoutId, @AuthenticationPrincipal User authUser) {
        Workout workoutToBeDeleted = workoutService.getWorkoutById(workoutId);

        if (!isAllowedToEditData(workoutToBeDeleted, authUser)) {
            String message = "You are not allowed to delete other user's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        workoutService.deleteWorkoutById(workoutId);
        String message = "You deleted workout with workoutId: " + workoutId;
        return ResponseEntity.ok(message);
    }

    @PostMapping("/{workoutId}")
    ResponseEntity<?> completeWorkout(@PathVariable Long workoutId, @AuthenticationPrincipal User authUser) {
        Workout workoutToComplete = workoutService.getWorkoutById(workoutId);

        if (!isAllowedToEditData(workoutToComplete, authUser)) {
            String message = "You cannot complete someone else's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        Workout completedWorkout = workoutService.completeWorkout(workoutId);
        return ResponseEntity.status(HttpStatus.OK).body(completedWorkout);
    }

    @GetMapping("/start-date/{startDate}/end-date/{endDate}")
    ResponseEntity<List<WorkoutResponse>> getWorkoutsBetweenDates(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @AuthenticationPrincipal User authUser
    ) {

        List<WorkoutResponse> workouts = workoutService.getUserWorkoutsBetweenDates(startDate, endDate, authUser.getId())
                .stream().map(WorkoutResponse::from).toList();

        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/user/all")
    ResponseEntity<List<Workout>> getUserWorkouts(@AuthenticationPrincipal User authUser) {
        List<Workout> userWorkouts = workoutService.getWorkoutsByUserId(authUser.getId());
        return new ResponseEntity<>(userWorkouts, HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<Workout>> getWorkouts() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        return new ResponseEntity<>(workouts, HttpStatus.OK);
    }

    private boolean isAllowedToEditData(Workout workout, User authUser) {
        // admins can edit whoever data
        if (UserUtils.isAdminUser(authUser)) return true;
        return !Objects.equals(workout.getUser().getId(), authUser.getId());
    }


}
