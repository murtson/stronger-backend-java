package com.pmstudios.stronger.loggedExercise;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseDto;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetDto;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetMapper;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exercise.ExerciseService;
import com.pmstudios.stronger.workout.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class LoggedExerciseServiceImpl implements LoggedExerciseService {

    LoggedSetMapper loggedSetMapper;
    LoggedExerciseRepository loggedExerciseRepository;
    WorkoutService workoutService;
    ExerciseService exerciseService;

    @Override
    public LoggedExercise getLoggedExercise(Long id) {
         return loggedExerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, LoggedExercise.class));
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
    public LoggedExercise save(LoggedExercise loggedExercise) {
        return loggedExerciseRepository.save(loggedExercise);
    }

    @Override
    public void deleteLoggedExercise(Long id) {
        loggedExerciseRepository.deleteById(id);
    }

    @Override
    public List<LoggedExercise> getLoggedExercisesByWorkoutId(Long workoutId) {
        return loggedExerciseRepository.findByWorkoutId(workoutId);
    }

    @Override
    public List<LoggedExercise> getLoggedExercisesByExerciseIdAndUserId(Long exerciseId, Long userId) {
        return loggedExerciseRepository.findByExerciseIdAndWorkout_User_Id(exerciseId, userId);
    }




}
