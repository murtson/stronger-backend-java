package com.pmstudios.stronger.workout;

import java.time.LocalDateTime;
import java.util.List;


public interface WorkoutService {

    Workout getWorkoutById(Long id);

    Workout saveWorkout(Workout workout);

    Workout completeWorkout(Long id);

    void deleteWorkoutById(Long id);

    List<Workout> getAllWorkouts();

    List<Workout> getWorkoutByUserId(Long userId);

    List<Workout> getUserWorkoutsBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Long userId);

}
