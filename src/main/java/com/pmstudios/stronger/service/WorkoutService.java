package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.Workout;

import java.util.List;


public interface WorkoutService {

    Workout getWorkout(Long id);

    List<Workout> getWorkouts();

    Workout saveWorkout(Workout workout, Long userId);

    void deleteWorkout(Long id);

}
