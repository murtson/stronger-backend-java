package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface LoggedSetRepository extends JpaRepository<LoggedSet, Long> {

    List<LoggedSet> findByLoggedExercise(Long loggedExercise);

    @Transactional
    void deleteAllByLoggedExerciseId(Long loggedExerciseId);

    List<LoggedSet> findByRepsAndLoggedExercise_Exercise_IdAndLoggedExercise_Workout_User_Id(Integer reps, Long ExerciseId, Long userId);







}