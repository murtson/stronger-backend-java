package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.LoggedSet;

import java.util.List;

public interface LoggedSetService {

    LoggedSet getLoggedSet(Long id);

    LoggedSet saveLoggedSet(LoggedSet loggedSet, Long loggedExerciseId);

    void deleteLoggedSet(Long id);

    LoggedSet updateLoggedSet(LoggedSet loggedSet);

    List<LoggedSet> saveLoggedSets(List<LoggedSet> loggedSets, Long loggedExerciseId);

    List<LoggedSet> getLoggedExerciseLoggedSets(Long loggedExerciseId);

}
