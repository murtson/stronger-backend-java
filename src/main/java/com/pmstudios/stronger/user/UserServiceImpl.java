package com.pmstudios.stronger.user;

import com.pmstudios.stronger.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    // TODO: make a DTO on User (remove password)


    public User getUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, User.class));
    }
    public User saveUser(User user) {
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User autheticateUser(User user) {
        return null;
    }


}
