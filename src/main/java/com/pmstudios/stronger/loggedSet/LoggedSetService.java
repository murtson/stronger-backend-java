package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;

import java.util.List;

public interface LoggedSetService {


    LoggedSet saveLoggedSet(LoggedSet loggedSet, LoggedExercise loggedExercise);

    List<LoggedSet> updateLoggedSets(List<LoggedSet> loggedSets, LoggedExercise loggedExercise);


}
