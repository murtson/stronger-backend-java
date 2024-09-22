package com.pmstudios.stronger.loggedExercise;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface LoggedExerciseRepository extends JpaRepository<LoggedExercise, Long> {
    List<LoggedExercise> findByWorkoutId(Long workoutId);

    // TODO: perhaps later set a user_id directly on LoggedExercise so we can remove  these convoluted query names
    List<LoggedExercise> findByExerciseIdAndWorkout_User_Id(Long exerciseId, Long userId);
}
