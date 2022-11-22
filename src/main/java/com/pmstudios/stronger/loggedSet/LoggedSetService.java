package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;

import javax.transaction.Transactional;
import java.util.List;

public interface LoggedSetService {


    LoggedSet saveLoggedSet(LoggedSet loggedSet, LoggedExercise loggedExercise);

    List<LoggedSet> updateLoggedSets(List<UpdateLoggedSetDTO> loggedSets, LoggedExercise loggedExercise);

    void deleteAllByLoggedExerciseId(Long loggedExerciseID);

}
