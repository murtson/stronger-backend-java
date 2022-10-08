package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.exception.ExerciseCategoryNotFoundException;
import com.pmstudios.stronger.respository.ExerciseCategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Optional<ExerciseCategory> exerciseCategory = exerciseCategoryRepository.findById(id);
        return unwrapExerciseCategory(exerciseCategory, id);
    }

    @Override
    public List<ExerciseCategory> getExerciseCategories() {
        return (List<ExerciseCategory>)exerciseCategoryRepository.findAll();
    }

    static ExerciseCategory unwrapExerciseCategory(Optional<ExerciseCategory> entity, Long id) {
        if(entity.isPresent()) return entity.get();
        else throw new ExerciseCategoryNotFoundException(id);
    }

}
