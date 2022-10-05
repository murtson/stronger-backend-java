package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.LoggedWorkoutSet;

public interface LoggedWorkoutSetService {

    LoggedWorkoutSet getLoggedWorkoutSet(Long id);

    LoggedWorkoutSet saveLoggedWorkoutSet(LoggedWorkoutSet loggedWorkoutSet, Long userWorkoutId);

    void deleteLoggedWorkoutSet(Long id);

}
