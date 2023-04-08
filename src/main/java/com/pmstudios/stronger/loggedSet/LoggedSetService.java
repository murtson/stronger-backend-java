package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;

import java.util.List;

public interface LoggedSetService {


    LoggedSet getById(Long id);

    LoggedSet save(LoggedSet loggedSet);

    void delete(LoggedSet loggedSet);

    void deleteByLoggedExerciseId(Long loggedExerciseId);

    List<LoggedSet> getByRepsAndExerciseAndUserId(int reps, Long exerciseId, Long userId);

    List<LoggedSet> addLoggedSet(Long loggedExerciseId, LoggedSet loggedSet);

    List<LoggedSet> removeLoggedSet(Long loggedSetId);


}
