package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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
