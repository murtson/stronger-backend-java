package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.Exercise;
import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.respository.ExerciseCategoryRepository;
import com.pmstudios.stronger.respository.ExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExerciseServiceImpl implements ExerciseService {

    ExerciseRepository exerciseRepository;

    ExerciseCategoryRepository exerciseCategoryRepository;

    @Override
    public Exercise getExercise(Long id) {
        return exerciseRepository.findById(id).get();
    }

    @Override
    public Exercise saveExercise(Exercise exercise, Long exerciseCategoryId) {
        ExerciseCategory exerciseCategory = exerciseCategoryRepository.findById(exerciseCategoryId).get();
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






/*    @Autowired
    private ExerciseRepository exerciseRepository;

    @Override
    public List<Exercise> getExercises() {
        return exerciseRepository.getExercises();
    }

    @Override
    public Exercise getExercise(int id) {
        int index = getExerciseIndex(id);
        return index == Constants.NOT_FOUND ?
                new Exercise() : exerciseRepository.getExercise(getExerciseIndex(id));
    }

    public int getExerciseIndex(int id)  {
        for (int index = 0; index < exerciseRepository.getExercises().size(); index++) {
            if(exerciseRepository.getExercise(index).getId() == id) return index;
        }
        throw new ExerciseNotFoundException(id);

    }

    @Override
    public void saveExercise(Exercise exercise) {
        exerciseRepository.saveExercise(exercise);
    }*/



}
