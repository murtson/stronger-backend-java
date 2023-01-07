package com.pmstudios.stronger.exercisePr;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExercisePrRepository extends JpaRepository<ExercisePr, Long> {

   ExercisePr findByRepsAndExerciseIdAndUserId(int Reps, Long exerciseId, Long UserId);

   List<ExercisePr> findByExerciseIdAndUserId(Long ExerciseId, Long UserId);

   List<ExercisePr> findByUserId(Long UserId);

}
