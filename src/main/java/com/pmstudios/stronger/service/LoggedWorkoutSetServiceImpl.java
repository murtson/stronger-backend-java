package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.LoggedWorkoutSet;
import com.pmstudios.stronger.entity.UserWorkout;
import com.pmstudios.stronger.respository.LoggedWorkoutSetRepository;
import com.pmstudios.stronger.respository.UserWorkoutRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LoggedWorkoutSetServiceImpl implements LoggedWorkoutSetService {

    LoggedWorkoutSetRepository loggedWorkoutSetRepository;

    UserWorkoutRepository userWorkoutRepository;

    @Override
    public LoggedWorkoutSet getLoggedWorkoutSet(Long id) {
        return loggedWorkoutSetRepository.findById(id).get();
    }

    @Override
    public LoggedWorkoutSet saveLoggedWorkoutSet(LoggedWorkoutSet loggedWorkoutSet, Long userWorkoutId) {
        UserWorkout userWorkout = userWorkoutRepository.findById(userWorkoutId).get();
        loggedWorkoutSet.setUserWorkout(userWorkout);
        return loggedWorkoutSetRepository.save(loggedWorkoutSet);
    }

    @Override
    public void deleteLoggedWorkoutSet(Long id) {
        loggedWorkoutSetRepository.deleteById(id);
    }
}
