package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.pojo.Exercise;
import com.pmstudios.stronger.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {

    @Autowired
    ExerciseService exerciseService;

    @GetMapping(value = "/all")
    public ResponseEntity<List<Exercise>> getExercises() {
        List<Exercise> exercises = exerciseService.getExercises();
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Exercise> getExercise(@PathVariable Long id)  {
        Exercise exercise = exerciseService.getExercise(id);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @PostMapping(value = "/exercise-category/{categoryId}")
    public ResponseEntity<Exercise> saveExercise(@PathVariable Long categoryId, @RequestBody Exercise exercise) {
        Exercise savedExercise = exerciseService.saveExercise(exercise, categoryId);
        return new ResponseEntity<>(savedExercise, HttpStatus.CREATED);
    }

    @GetMapping(value = "/exercise-category/{categoryId}")
    public ResponseEntity<List<Exercise>> getExerciseCategoryExercises(@PathVariable Long categoryId) {
        List<Exercise> exercises = exerciseService.getExerciseCategoryExercises(categoryId);
        return new ResponseEntity<>(exercises, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Exercise> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
