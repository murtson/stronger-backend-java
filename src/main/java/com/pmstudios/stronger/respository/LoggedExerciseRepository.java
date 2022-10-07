package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.entity.LoggedExercise;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoggedExerciseRepository extends CrudRepository<LoggedExercise, Long> {
    List<LoggedExercise> findByWorkoutId(Long workoutId);

}
