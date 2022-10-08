package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.entity.Workout;
import com.pmstudios.stronger.exception.UserNotFoundException;
import com.pmstudios.stronger.exception.WorkoutNotFoundException;
import com.pmstudios.stronger.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public User getUser(Long id) {
        Optional<User> user = userRepository.findById(id);
        return unwrapUser(user, id);
    }
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }
    private User unwrapUser(Optional<User> entity, Long id) {
        if (entity.isPresent()) return entity.get();
        else throw new UserNotFoundException(id);
    }

}
