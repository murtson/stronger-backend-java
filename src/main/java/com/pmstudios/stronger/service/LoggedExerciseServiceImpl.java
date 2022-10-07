package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.Exercise;
import com.pmstudios.stronger.entity.LoggedExercise;
import com.pmstudios.stronger.entity.LoggedSet;
import com.pmstudios.stronger.entity.Workout;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.respository.LoggedExerciseRepository;
import com.pmstudios.stronger.respository.LoggedSetRepository;
import com.pmstudios.stronger.respository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LoggedExerciseServiceImpl implements LoggedExerciseService {

    WorkoutRepository workoutRepository;
    LoggedExerciseRepository loggedExerciseRepository;
    ExerciseRepository exerciseRepository;
    LoggedSetService loggedSetService;

    @Override
    public LoggedExercise getLoggedExercise(Long id) {
        return loggedExerciseRepository.findById(id).get();
    }

    @Override
    public LoggedExercise saveLoggedExercise(LoggedExercise loggedExercise, Long WorkoutId, Long exerciseId) {
        Workout workout = workoutRepository.findById(WorkoutId).get();
        Exercise exercise = exerciseRepository.findById(exerciseId).get();
        loggedExercise.setWorkout(workout);
        loggedExercise.setExercise(exercise);
        return loggedExerciseRepository.save(loggedExercise);
    }

    @Override
    public LoggedExercise updateLoggedExerciseSets(Long loggedExerciseId, List<LoggedSet> loggedSets) {
        LoggedExercise loggedExercise = this.getLoggedExercise(loggedExerciseId);
        List<LoggedSet> updatedSets = loggedSetService.saveLoggedSets(loggedSets, loggedExerciseId);
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
