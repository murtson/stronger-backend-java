package com.pmstudios.stronger.exercisePr;

import com.pmstudios.stronger.exercisePr.dto.ExercisePrResponse;
import com.pmstudios.stronger.user.User;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/exercise-pr")
public class ExercisePrController {

    ExercisePrService exercisePrService;

    @GetMapping(value = "/all/me")
    public ResponseEntity<List<ExercisePrResponse>> getUserExercisePrs(@AuthenticationPrincipal User authUser) {
        List<ExercisePrResponse> response = exercisePrService.getAll(authUser.getId())
                .stream().map(ExercisePrResponse::from).toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/exercise/{exerciseId}/me")
    public ResponseEntity<List<ExercisePrResponse>> getSpecificExerciseUserPrs(
            @PathVariable Long exerciseId, @AuthenticationPrincipal User authUser) {
        List<ExercisePrResponse> response = exercisePrService.getByExercise(exerciseId, authUser.getId())
                .stream().map(ExercisePrResponse::from).toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/exercise/{exerciseId}/reps/{reps}/me")
    public ResponseEntity<ExercisePrResponse> getExercisePrByRepsAndExercise(@PathVariable Long exerciseId, @PathVariable Long userId, @RequestParam int reps) {
        ExercisePr exercisePR = exercisePrService.getByRepsAndExerciseAndUserId(reps, exerciseId, userId);
        return new ResponseEntity<>(ExercisePrResponse.from(exercisePR), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteExercisePr(@PathVariable Long id) {
        exercisePrService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
