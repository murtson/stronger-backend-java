package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.LoggedExercise;
import com.pmstudios.stronger.entity.LoggedSet;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.respository.LoggedSetRepository;
import com.pmstudios.stronger.respository.LoggedExerciseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class LoggedSetServiceImpl implements LoggedSetService {

    LoggedSetRepository loggedSetRepository;

    LoggedExerciseRepository loggedExerciseRepository;

    ExerciseRepository exerciseRepository;


    @Override
    public LoggedSet getLoggedSet(Long id) {
        return null;
    }

    @Override
    public LoggedSet saveLoggedSet(LoggedSet loggedSet, Long loggedExerciseId) {
        LoggedExercise loggedExercise = loggedExerciseRepository.findById(loggedExerciseId).get();
        loggedSet.setLoggedExercise(loggedExercise);
        return loggedSetRepository.save(loggedSet);
    }

    @Override
    public void deleteLoggedSet(Long id) {

    }

    @Override
    public LoggedSet updateLoggedSet(LoggedSet loggedSet) {
        return null;
    }

    @Override
    public List<LoggedSet> saveLoggedSets(List<LoggedSet> loggedSets, Long loggedExerciseId) {
        // TODO: rewrite with lambda?
        List<LoggedSet> updatedLoggedSets = new ArrayList<>();
        for (LoggedSet set : loggedSets) {
            LoggedSet createdSet = this.saveLoggedSet(set, loggedExerciseId);
            updatedLoggedSets.add(createdSet);
        }
        return updatedLoggedSets;
    }


    @Override
    public List<LoggedSet> getLoggedExerciseLoggedSets(Long loggedExerciseId) {
        return null;
    }
}
