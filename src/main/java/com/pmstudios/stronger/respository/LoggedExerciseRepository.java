package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.entity.LoggedExercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoggedExerciseRepository extends JpaRepository<LoggedExercise, Long> {
    List<LoggedExercise> findByWorkoutId(Long workoutId);

}
