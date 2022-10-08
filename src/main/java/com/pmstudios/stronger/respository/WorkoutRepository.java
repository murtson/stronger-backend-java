package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.entity.Workout;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WorkoutRepository extends CrudRepository<Workout, Long> {
    List<Workout> findByUserId(Long userId);

}
