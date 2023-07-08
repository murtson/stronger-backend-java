package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.NumberUtility;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.dto.AddLoggedSetRequest;

import java.util.List;

public interface LoggedSetService {


    static Double getOneRepMaxEstimate(Double weight, int reps) {
        if (reps == 1) return weight;
        // We use the Brzycki formula from Matt Brzycki to calculate 1RM: weight / (1.0278 - 0.0287 * reps)
        Double divisor = 1.0278 - 0.0287 * reps;
        return NumberUtility.round(weight / divisor, 2);
    }

    static LoggedSet from(AddLoggedSetRequest request) {
        return new LoggedSet(request.getWeight(), request.getReps(),
                getOneRepMaxEstimate(request.getWeight(), request.getReps()), false);
    }

    LoggedSet getById(Long id);

    LoggedSet save(LoggedSet loggedSet);

    void delete(LoggedSet loggedSet);

    void deleteByLoggedExerciseId(Long loggedExerciseId);

    List<LoggedSet> getByRepsAndExerciseAndUserId(int reps, Long exerciseId, Long userId);

    List<LoggedSet> addLoggedSet(LoggedExercise loggedExercise, LoggedSet loggedSet);

    List<LoggedSet> removeLoggedSet(LoggedSet loggedSet);

}
