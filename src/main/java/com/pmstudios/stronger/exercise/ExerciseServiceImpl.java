package com.pmstudios.stronger.exercise;

import com.pmstudios.stronger.exerciseCategory.ExerciseCategory;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategoryService;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    ExerciseRepository exerciseRepository;
    ExerciseCategoryService exerciseCategoryService;

    @Override
    public Exercise getExercise(Long exerciseId) {
        return exerciseRepository.findById(exerciseId)
                .orElseThrow(() -> new EntityNotFoundException(exerciseId, Exercise.class));
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
