package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.pojo.Exercise;
import com.pmstudios.stronger.pojo.ExerciseCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


// Spring creates an exerciseRepositoryImpl from this interrface that lives inside the spring container as a bean
// it inherits save, findById, exitsByid
// we can implement our own queries as long as we follow a specific naming convention
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {
    List<Exercise> findByExerciseCategoryId(Long exerciseCategoryId);

}
