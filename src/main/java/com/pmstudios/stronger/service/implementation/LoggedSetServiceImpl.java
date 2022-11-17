package com.pmstudios.stronger.service.implementation;

import com.pmstudios.stronger.entity.LoggedExercise;
import com.pmstudios.stronger.entity.LoggedSet;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.respository.LoggedSetRepository;
import com.pmstudios.stronger.respository.LoggedExerciseRepository;
import com.pmstudios.stronger.service.LoggedSetService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LoggedSetServiceImpl implements LoggedSetService {

    LoggedSetRepository loggedSetRepository;

    @Override
    public LoggedSet saveLoggedSet(LoggedSet set, LoggedExercise loggedExercise) {
        set.setLoggedExercise(loggedExercise);
        return loggedSetRepository.save(set);
    }

    @Override
    public List<LoggedSet> updateLoggedSets(List<LoggedSet> loggedSets, LoggedExercise loggedExercise) {
        return loggedSets.stream()
                .map(set -> saveLoggedSet(set, loggedExercise))
                .collect(Collectors.toList());
    }
}
