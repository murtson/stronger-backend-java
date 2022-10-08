package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.entity.Workout;
import com.pmstudios.stronger.exception.UserNotFoundException;
import com.pmstudios.stronger.exception.WorkoutNotFoundException;
import com.pmstudios.stronger.respository.UserRepository;
import com.pmstudios.stronger.respository.WorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WorkoutServiceImpl implements WorkoutService {

    WorkoutRepository workoutRepository;
    UserService userService;

    @Override
    public Workout getWorkout(Long id) {
        Optional<Workout> workout = workoutRepository.findById(id);
        return unwrapWorkout(workout, id);
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
        return (List<Workout>) workoutRepository.findAll();
    }

    @Override
    public List<Workout> getUserWorkouts(Long userId) {
        return workoutRepository.findByUserId(userId);
    }

    private Workout unwrapWorkout(Optional<Workout> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new WorkoutNotFoundException(id);
    }

}
