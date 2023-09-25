package com.pmstudios.stronger.user;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;


public interface UserService extends UserDetailsService {

    User getUserById(Long id);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    User saveUser(User user);

    void deleteUser(Long id);

    List<User> getUsers();

    User autheticateUser(User user);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
