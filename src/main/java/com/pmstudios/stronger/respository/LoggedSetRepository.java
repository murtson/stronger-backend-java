package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.entity.LoggedSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoggedSetRepository extends JpaRepository<LoggedSet, Long> {
    List<LoggedSet> findByLoggedExercise(Long workoutSetId);
}
