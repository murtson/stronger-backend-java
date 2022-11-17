package com.pmstudios.stronger.workout;

import java.time.LocalDateTime;
import java.util.List;


public interface WorkoutService {

    Workout getWorkout(Long id);
    Workout createWorkout(Workout workout, Long userId);
    Workout completeWorkout(Long id);
    void deleteWorkout(Long id);
    List<Workout> getWorkouts();
    List<Workout> getUserWorkouts(Long userId);
    List<Workout> getUserWorkoutsBetweenDates(LocalDateTime startDate, LocalDateTime endDate, Long userId);

}
