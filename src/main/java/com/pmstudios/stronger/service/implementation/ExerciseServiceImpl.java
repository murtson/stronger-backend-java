package com.pmstudios.stronger.service.implementation;

import com.pmstudios.stronger.entity.Exercise;
import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.service.ExerciseCategoryService;
import com.pmstudios.stronger.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    ExerciseRepository exerciseRepository;
    ExerciseCategoryService exerciseCategoryService;

    @Override
    public Exercise getExercise(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Exercise.class));
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
        return exerciseRepository.findAll();
    }

    @Override
    public List<Exercise> getExerciseCategoryExercises(Long exerciseCategoryId) {
        return exerciseRepository.findByExerciseCategoryId(exerciseCategoryId);
    }

}
