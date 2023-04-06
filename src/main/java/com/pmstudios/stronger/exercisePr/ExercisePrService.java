package com.pmstudios.stronger.exercisePr;

import java.util.List;

public interface ExercisePrService {

//    void deleteExercisePR(Long Id);

    void deleteExercisePr(ExercisePr exercisePr);

    void deleteExercisePrById(Long exercisePrId);
    ExercisePr saveExercisePr(ExercisePr exercisePR);
    ExercisePr getByRepsAndExerciseAndUserId(int reps, Long ExerciseId, Long userId);
    List<ExercisePr> getSpecificExercisePrs(Long exerciseId, Long userId);
    List<ExercisePr> getAllExercisePrs(Long userId);
    ExercisePrDto toDto(ExercisePr exercisePR);

}
