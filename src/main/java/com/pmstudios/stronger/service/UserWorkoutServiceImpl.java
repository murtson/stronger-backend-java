package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.entity.UserWorkout;
import com.pmstudios.stronger.respository.UserRepository;
import com.pmstudios.stronger.respository.UserWorkoutRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UserWorkoutServiceImpl implements UserWorkoutService {

    UserWorkoutRepository userWorkoutRepository;

    UserRepository userRepository;

    @Override
    public UserWorkout getUserWorkout(Long id) {
        return userWorkoutRepository.findById(id).get();
    }

    @Override
    public List<UserWorkout> getUserWorkouts() {
        return (List<UserWorkout>) userWorkoutRepository.findAll();
    }

    @Override
    public UserWorkout saveUserWorkout(UserWorkout userWorkout, Long userId) {
        User user = userRepository.findById(userId).get();
        userWorkout.setUser(user);
        return userWorkoutRepository.save(userWorkout);
    }

    @Override
    public void deleteUserWorkout(Long id) {
        userWorkoutRepository.deleteById(id);
    }
}
