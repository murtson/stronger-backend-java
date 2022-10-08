package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.Workout;

import java.util.List;


public interface WorkoutService {

    Workout getWorkout(Long id);
    Workout saveWorkout(Workout workout, Long userId);
    void deleteWorkout(Long id);
    List<Workout> getWorkouts();
    List<Workout> getUserWorkouts(Long userId);

}
