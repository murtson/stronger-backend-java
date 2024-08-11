package com.pmstudios.stronger.loggedExercise;

import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseResponse;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.loggedSet.LoggedSetService;
import com.pmstudios.stronger.loggedSet.dto.AddLoggedSetRequest;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.auth.dto.UserUtils;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@RequestMapping("/logged-exercise")
public class LoggedExerciseController {

    private final LoggedExerciseService loggedExerciseService;
    private final WorkoutService workoutService;
    private final LoggedSetService loggedSetService;

    @PostMapping("/workout/{workoutId}/exercise/{exerciseId}")
    ResponseEntity<?> createLoggedExercise(@PathVariable Long workoutId,
                                           @PathVariable Long exerciseId,
                                           @RequestBody AddLoggedSetRequest requestBody,
                                           @AuthenticationPrincipal User authUser) {
        // TODO: fix so that loggedSetsDTO can be sent with the req
        Workout workout = workoutService.getWorkoutById(workoutId);

        if (!isModifyingOwnData(workout, authUser) && !UserUtils.isAdminUser(authUser)) {
            String message = "You are not allowed to modify other user's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        LoggedExercise createdLoggedExercise = loggedExerciseService.create(workout, exerciseId);
        List<LoggedSet> addedSets = loggedSetService.addLoggedSet(createdLoggedExercise, LoggedSetService.from(requestBody));
        createdLoggedExercise.setLoggedSets(addedSets);

        LoggedExerciseResponse response = LoggedExerciseResponse.from(createdLoggedExercise);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    ResponseEntity<LoggedExerciseResponse> getLoggedExercise(@PathVariable Long id) {
        LoggedExercise loggedExercise = loggedExerciseService.getById(id);
        LoggedExerciseResponse response = LoggedExerciseResponse.from(loggedExercise);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{loggedExerciseId}")
    ResponseEntity<?> deleteLoggedExercise(@PathVariable Long loggedExerciseId, @AuthenticationPrincipal User authUser) {
        LoggedExercise loggedExerciseToDelete = loggedExerciseService.getById(loggedExerciseId);

        if (!isModifyingOwnData(loggedExerciseToDelete, authUser) && !UserUtils.isAdminUser(authUser)) {
            String message = "You are not allowed to modify other user's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        loggedExerciseService.delete(loggedExerciseToDelete);
        String message = "You deleted loggedExercise with id: " + loggedExerciseId;
        return ResponseEntity.ok(message);
    }


    @GetMapping("/workout/{workoutId}")
    ResponseEntity<List<LoggedExerciseResponse>> getLoggedExercisesByWorkoutId(@PathVariable Long workoutId) {
        List<LoggedExercise> loggedExercises = loggedExerciseService.getByWorkoutId(workoutId);
        List<LoggedExerciseResponse> response = loggedExercises.stream()
                .map(LoggedExerciseResponse::from).toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exercise/{exerciseId}/user/{userId}")
    ResponseEntity<List<LoggedExercise>> getLoggedExercisesByExerciseIdAndUserId(@PathVariable Long exerciseId, @PathVariable Long userId) {
        List<LoggedExercise> loggedExercises = loggedExerciseService.getByExerciseIdAndUserId(exerciseId, userId);
        return ResponseEntity.ok(loggedExercises);
    }


    private boolean isModifyingOwnData(Workout workout, User authUser) {
        return Objects.equals(workout.getUser().getId(), authUser.getId());
    }

    private boolean isModifyingOwnData(LoggedExercise loggedExercise, User authUser) {
        return Objects.equals(loggedExercise.getWorkout().getUser().getId(), authUser.getId());
    }


}
