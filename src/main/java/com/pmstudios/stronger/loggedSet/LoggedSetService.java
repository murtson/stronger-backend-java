package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;

import java.util.List;

public interface LoggedSetService {


    LoggedSet saveLoggedSet(LoggedSet loggedSet);

    void deleteByLoggedExerciseId(Long loggedExerciseId);

    LoggedSet getTopLoggedSet(List<LoggedSet> loggedSets);

    List<LoggedSet> getByRepsAndExerciseAndUserId(int reps, Long exerciseId, Long userId);

    List<LoggedSet> createLoggedSet(Long loggedExerciseId, LoggedSet loggedSet);

    boolean isNewTopLoggedSet(LoggedSet newLoggedSet, List<LoggedSet> previousLoggedSets);

    void updateExercisePrIfImproved(LoggedExercise loggedExercise, LoggedSet loggedSet);

}
