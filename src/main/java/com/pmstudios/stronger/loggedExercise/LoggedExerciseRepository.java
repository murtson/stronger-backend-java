package com.pmstudios.stronger.loggedExercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LoggedExerciseRepository extends JpaRepository<LoggedExercise, Long> {
    List<LoggedExercise> findByWorkoutId(Long workoutId);

    List<LoggedExercise> findByExerciseIdAndWorkout_User_Id(Long exerciseId, Long userId);


}
