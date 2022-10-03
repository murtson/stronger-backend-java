package com.pmstudios.stronger.controller;

import com.pmstudios.stronger.pojo.Exercise;
import com.pmstudios.stronger.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Exercise> getExercise(@PathVariable int id)  {
        Exercise exercise = exerciseService.getExercise(id);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }


}
