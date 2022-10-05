package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.UserWorkout;

import java.util.List;


public interface UserWorkoutService {

    UserWorkout getUserWorkout(Long id);

    List<UserWorkout> getUserWorkouts();

    UserWorkout saveUserWorkout(UserWorkout userWorkout, Long userId);

    void deleteUserWorkout(Long id);

}
