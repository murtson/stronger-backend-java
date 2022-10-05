package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.respository.ExerciseCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
        return exerciseCategoryRepository.findById(id).get();
    }

    @Override
    public List<ExerciseCategory> getExerciseCategories() {
        return (List<ExerciseCategory>)exerciseCategoryRepository.findAll();
    }
}
