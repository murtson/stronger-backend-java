package com.pmstudios.stronger.service.implementation;

import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.entity.Workout;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.respository.WorkoutRepository;
import com.pmstudios.stronger.service.UserService;
import com.pmstudios.stronger.service.WorkoutService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class WorkoutServiceImpl implements WorkoutService {

    WorkoutRepository workoutRepository;
    UserService userService;

    @Override
    public Workout getWorkout(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Workout.class));
    }

    @Override
    public Workout saveWorkout(Workout workout, Long userId) {
        User user = userService.getUser(userId);
        workout.setUser(user);
        return workoutRepository.save(workout);
    }

    @Override
    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public List<Workout> getWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public List<Workout> getUserWorkouts(Long userId) {
        return workoutRepository.findByUserId(userId);
    }

}
