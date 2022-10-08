package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.Exercise;
import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.exception.ExerciseNotFoundException;
import com.pmstudios.stronger.exception.UserNotFoundException;
import com.pmstudios.stronger.respository.ExerciseCategoryRepository;
import com.pmstudios.stronger.respository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    ExerciseRepository exerciseRepository;
    ExerciseCategoryService exerciseCategoryService;

    @Override
    public Exercise getExercise(Long id) {
        Optional<Exercise> exercise = exerciseRepository.findById(id);
        return unwrapExercise(exercise, id);
    }

    @Override
    public Exercise saveExercise(Exercise exercise, Long exerciseCategoryId) {
        ExerciseCategory exerciseCategory = exerciseCategoryService.getExerciseCategory(exerciseCategoryId);
        exercise.setExerciseCategory(exerciseCategory);
        return exerciseRepository.save(exercise);
    }

    @Override
    public void deleteExercise(Long id) {
        exerciseRepository.deleteById(id);
    }

    @Override
    public List<Exercise> getExercises() {
        return (List<Exercise>)exerciseRepository.findAll();
    }

    @Override
    public List<Exercise> getExerciseCategoryExercises(Long exerciseCategoryId) {
        return exerciseRepository.findByExerciseCategoryId(exerciseCategoryId);
    }

    private Exercise unwrapExercise(Optional<Exercise> entity, Long id) {
        if(entity.isPresent()) return entity.get();
        else throw new ExerciseNotFoundException(id);
    }

}
