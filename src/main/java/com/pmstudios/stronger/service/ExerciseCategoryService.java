package com.pmstudios.stronger.service;

import com.pmstudios.stronger.pojo.Exercise;
import com.pmstudios.stronger.pojo.ExerciseCategory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ExerciseCategoryService {

    ExerciseCategory getExerciseCategory(Long id);

    ExerciseCategory saveExerciseCategory(ExerciseCategory exerciseCategory);

    void deleteExerciseCategory(Long id);

    List<ExerciseCategory> getExerciseCategories();

}
