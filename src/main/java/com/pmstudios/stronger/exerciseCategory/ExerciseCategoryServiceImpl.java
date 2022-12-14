package com.pmstudios.stronger.exerciseCategory;

import com.pmstudios.stronger.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseCategoryServiceImpl implements ExerciseCategoryService {

    ExerciseCategoryRepository exerciseCategoryRepository;

    @Override
    public ExerciseCategory saveExerciseCategory(ExerciseCategory exerciseCategory) {
        return exerciseCategoryRepository.save(exerciseCategory);
    }

    @Override
    public void deleteExerciseCategory(Long id) {
        exerciseCategoryRepository.deleteById(id);
    }

    @Override
    public ExerciseCategory getExerciseCategory(Long id) {
        return exerciseCategoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, ExerciseCategory.class));
    }

    @Override
    public List<ExerciseCategory> getExerciseCategories() {
        return exerciseCategoryRepository.findAll();
    }

}
