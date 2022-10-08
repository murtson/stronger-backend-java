package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.Exercise;
import com.pmstudios.stronger.entity.LoggedExercise;
import com.pmstudios.stronger.entity.LoggedSet;
import com.pmstudios.stronger.entity.Workout;
import com.pmstudios.stronger.exception.LoggedExerciseNotFoundException;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.respository.LoggedExerciseRepository;
import com.pmstudios.stronger.respository.LoggedSetRepository;
import com.pmstudios.stronger.respository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class LoggedExerciseServiceImpl implements LoggedExerciseService {


    LoggedExerciseRepository loggedExerciseRepository;


    WorkoutService workoutService;

    ExerciseService exerciseService;
    LoggedSetService loggedSetService;

    @Override
    public LoggedExercise getLoggedExercise(Long id) {
        Optional<LoggedExercise> loggedExercise = loggedExerciseRepository.findById(id);
        if (loggedExercise.isPresent()) return loggedExercise.get();
        else throw new LoggedExerciseNotFoundException(id);
    }

    @Override
    public LoggedExercise saveLoggedExercise(LoggedExercise loggedExercise, Long workoutId, Long exerciseId) {
        Workout workout = workoutService.getWorkout(workoutId);
        Exercise exercise = exerciseService.getExercise(exerciseId);
        loggedExercise.setWorkout(workout);
        loggedExercise.setExercise(exercise);
        return loggedExerciseRepository.save(loggedExercise);
    }

    @Override
    public LoggedExercise updateLoggedExerciseSets(Long loggedExerciseId, List<LoggedSet> loggedSets) {
        LoggedExercise loggedExercise = this.getLoggedExercise(loggedExerciseId);
        List<LoggedSet> updatedSets = loggedSetService.updateLoggedSets(loggedSets, loggedExercise);
        loggedExercise.setLoggedSets(updatedSets);
        return loggedExercise;
    }

    @Override
    public void deleteLoggedExercise(Long id) {
        loggedExerciseRepository.deleteById(id);
    }

    @Override
    public List<LoggedExercise> getWorkoutLoggedExercises(Long workoutId) {
        return loggedExerciseRepository.findByWorkoutId(workoutId);
    }


}
