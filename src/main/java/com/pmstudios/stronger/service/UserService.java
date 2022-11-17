package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    User getUser(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    List<User> getUsers();
    User autheticateUser(User user);
}
