package com.pmstudios.stronger.respository;

import com.pmstudios.stronger.pojo.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


public interface ExerciseRepository extends CrudRepository<Exercise, Long> {

}
