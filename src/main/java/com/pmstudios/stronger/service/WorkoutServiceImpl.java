package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.entity.Workout;
import com.pmstudios.stronger.respository.UserRepository;
import com.pmstudios.stronger.respository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WorkoutServiceImpl implements WorkoutService {

    WorkoutRepository workoutRepository;

    UserRepository userRepository;

    @Override
    public Workout getWorkout(Long id) {
        return workoutRepository.findById(id).get();
    }

    @Override
    public List<Workout> getWorkouts() {
        // TODO: should only be the user's workouts
        return (List<Workout>) workoutRepository.findAll();
    }

    @Override
    public Workout saveWorkout(Workout workout, Long userId) {
        User user = userRepository.findById(userId).get();
        workout.setUser(user);
        return workoutRepository.save(workout);
    }

    @Override
    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }
}
