package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetUpdateDto;

import java.util.List;

public interface LoggedSetService {


    LoggedSet saveLoggedSet(LoggedSet loggedSet);

    List<LoggedSet> updateLoggedSets(Long loggedExerciseId, List<LoggedSet> loggedSets);

    void deleteByLoggedExerciseId(Long loggedExerciseId);

    LoggedSet getBestLoggedSet(List<LoggedSet> loggedSets);

    List<LoggedSet> getByRepsAndExerciseAndUserId(int reps, Long exerciseId, Long userId);

}
