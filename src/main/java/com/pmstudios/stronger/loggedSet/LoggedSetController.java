package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedExercise.LoggedExerciseService;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetResponse;
import com.pmstudios.stronger.loggedSet.dto.AddLoggedSetRequest;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.auth.dto.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/logged-set")
@RequiredArgsConstructor
public class LoggedSetController {

    private final LoggedExerciseService loggedExerciseService;
    private final LoggedSetService loggedSetService;

    @PostMapping("/logged-exercise/{loggedExerciseId}")
    ResponseEntity<?> addLoggedSet(@PathVariable Long loggedExerciseId,
                                   @Valid @RequestBody AddLoggedSetRequest request,
                                   @AuthenticationPrincipal User authUser) {
        LoggedExercise loggedExercise = loggedExerciseService.getById(loggedExerciseId);

        if (!isModifyingOwnData(loggedExercise, authUser) && !UserUtils.isAdminUser(authUser)) {
            String message = "You are not allowed to modify other user's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        LoggedSet loggedSet = LoggedSetService.from(request);
        List<LoggedSet> updatedLoggedSets = loggedSetService.addLoggedSet(loggedExercise, loggedSet);

        List<LoggedSetResponse> response = updatedLoggedSets.stream().map(LoggedSetResponse::from).toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{loggedSetId}")
    ResponseEntity<?> updateLoggedSet(@PathVariable Long loggedSetId,
                                      @Valid @RequestBody AddLoggedSetRequest request,
                                      @AuthenticationPrincipal User authUser) {
        LoggedSet oldLoggedSet = loggedSetService.getById(loggedSetId);
        LoggedSet newLoggedSet = LoggedSetService.from(request);

        if (!isModifyingOwnData(oldLoggedSet, authUser) && !UserUtils.isAdminUser(authUser)) {
            String message = "You are not allowed to modify other user's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        List<LoggedSet> updatedLoggedSets = loggedSetService.updateLoggedSet(oldLoggedSet, newLoggedSet);
        List<LoggedSetResponse> response = updatedLoggedSets.stream().map(LoggedSetResponse::from).toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{loggedSetId}")
    ResponseEntity<?> deleteLoggedSet(@PathVariable Long loggedSetId, @AuthenticationPrincipal User authUser) {
        LoggedSet loggedSetToRemove = loggedSetService.getById(loggedSetId);

        if (!isModifyingOwnData(loggedSetToRemove, authUser) && !UserUtils.isAdminUser(authUser)) {
            String message = "You are not allowed to modify other user's workouts";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        List<LoggedSet> updatedLoggedSets = loggedSetService.removeLoggedSet(loggedSetToRemove);
        List<LoggedSetResponse> response = updatedLoggedSets.stream().map(LoggedSetResponse::from).toList();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/exercise/{exerciseId}/reps/{repsAmount}")
    ResponseEntity<List<LoggedSetResponse>> getLoggedSetsByRepsAndExercise(@PathVariable int repsAmount,
                                                                           @PathVariable Long exerciseId) {
        List<LoggedSet> loggedSets = loggedSetService.getByRepsAndExerciseAndUserId(repsAmount, exerciseId, 1L);
        List<LoggedSetResponse> loggedSetsDto = loggedSets.stream().map(LoggedSetResponse::from).toList();
        return new ResponseEntity<>(loggedSetsDto, HttpStatus.OK);
    }


    private boolean isModifyingOwnData(LoggedSet loggedSet, User authUser) {
        return Objects.equals(loggedSet.getLoggedExercise().getWorkout().getUser().getId(), authUser.getId());
    }

    private boolean isModifyingOwnData(LoggedExercise loggedExercise, User authUser) {
        return Objects.equals(loggedExercise.getWorkout().getUser().getId(), authUser.getId());
    }

}
