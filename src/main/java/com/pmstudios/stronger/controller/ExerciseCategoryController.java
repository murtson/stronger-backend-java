package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.pojo.Exercise;
import com.pmstudios.stronger.pojo.ExerciseCategory;
import com.pmstudios.stronger.service.ExerciseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise-category")
public class ExerciseCategoryController {

    @Autowired
    ExerciseCategoryService exerciseCategoryService;

    @GetMapping("/all")
    ResponseEntity<List<ExerciseCategory>> getExerciseCategories() {
        List<ExerciseCategory> categories = exerciseCategoryService.getExerciseCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    ResponseEntity<ExerciseCategory> getExerciseCategory(@PathVariable Long id) {
        ExerciseCategory category = exerciseCategoryService.getExerciseCategory(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }


    @PostMapping()
    ResponseEntity<ExerciseCategory> saveExerciseCategory(@RequestBody ExerciseCategory exerciseCategory) {
        ExerciseCategory category = exerciseCategoryService.saveExerciseCategory(exerciseCategory);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<HttpStatus> deleteExerciseCategory(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
