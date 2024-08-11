package com.pmstudios.stronger.exerciseCategory;

import com.pmstudios.stronger.exerciseCategory.dto.ExerciseCategoryResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/exercise-category")
public class ExerciseCategoryController {
    ExerciseCategoryService exerciseCategoryService;

    @GetMapping("/all")
    ResponseEntity<List<ExerciseCategoryResponse>> getExerciseCategories() {
        List<ExerciseCategoryResponse> categories = exerciseCategoryService.getExerciseCategories().stream()
                .map(ExerciseCategoryResponse::from).toList();

        return ResponseEntity.ok(categories);
    }

    @GetMapping("/{id}")
    ResponseEntity<ExerciseCategory> getExerciseCategory(@PathVariable Long id) {
        ExerciseCategory category = exerciseCategoryService.getExerciseCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping()
    ResponseEntity<ExerciseCategory> saveExerciseCategory(@Valid @RequestBody ExerciseCategory exerciseCategory) {
        ExerciseCategory category = exerciseCategoryService.saveExerciseCategory(exerciseCategory);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteExerciseCategory(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
