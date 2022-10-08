package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.LoggedExercise;
import com.pmstudios.stronger.entity.LoggedSet;

import java.util.List;

public interface LoggedSetService {


    LoggedSet saveLoggedSet(LoggedSet loggedSet, LoggedExercise loggedExercise);

    List<LoggedSet> updateLoggedSets(List<LoggedSet> loggedSets, LoggedExercise loggedExercise);


}
