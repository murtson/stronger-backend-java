package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.entity.LoggedSet;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LoggedSetRepository extends CrudRepository<LoggedSet, Long> {
    List<LoggedSet> findByLoggedExercise(Long workoutSetId);
}
