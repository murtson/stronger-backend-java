package com.pmstudios.stronger.exercisePr;

import java.util.List;

public interface ExercisePrService {

//    void deleteExercisePR(Long Id);

    void delete(ExercisePr exercisePr);

    void deleteById(Long exercisePrId);

    ExercisePr save(ExercisePr exercisePR);

    ExercisePr getByRepsAndExerciseAndUserId(int reps, Long ExerciseId, Long userId);

    List<ExercisePr> getByExercise(Long exerciseId, Long userId);

    List<ExercisePr> getAll(Long userId);

}
