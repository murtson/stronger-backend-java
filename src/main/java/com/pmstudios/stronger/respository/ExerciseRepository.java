package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


// Spring creates an exerciseRepositoryImpl from this interrface that lives inside the spring container as a bean
// it inherits save, findById, exitsByid
// we can implement our own queries as long as we follow a specific naming convention
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByExerciseCategoryId(Long exerciseCategoryId);

}
