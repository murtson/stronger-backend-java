package com.pmstudios.stronger.exercisePr;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/exercise-pr")
public class ExercisePrController {

    ExercisePrService exercisePrService;


    @GetMapping(value = "/user/{userId}/all")
    public ResponseEntity<List<ExercisePrDto>> getAllExercisePRs(@PathVariable Long userId) {
        List<ExercisePrDto> exercisePrs = exercisePrService.getAllExercisePrs(userId).stream()
                .map(e -> exercisePrService.toDto(e)).toList();

        return new ResponseEntity<>(exercisePrs, HttpStatus.OK);
    }

    @GetMapping(value = "/exercise/{exerciseId}/user/{userId}/all")
    public ResponseEntity<List<ExercisePrDto>> getSpecificExercisePRs(@PathVariable Long exerciseId, @PathVariable Long userId) {
        List<ExercisePrDto> exercisePRs = exercisePrService.getSpecificExercisePrs(exerciseId, userId).stream()
                .map(e -> exercisePrService.toDto(e)).toList();

        return new ResponseEntity<>(exercisePRs, HttpStatus.OK);
    }

    @GetMapping(value = "/exercise/{exerciseId}/user/{userId}")
    public ResponseEntity<ExercisePrDto> getExercisesPR(@PathVariable Long exerciseId, @PathVariable Long userId, @RequestParam int reps) {
        ExercisePr exercisePR = exercisePrService.getByRepsAndExerciseAndUserId(reps, exerciseId, userId);
        return new ResponseEntity<>(exercisePrService.toDto(exercisePR), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Long id) {
        exercisePrService.deleteExercisePR(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
