package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {

    User saveUser(User user);

    User getUser(Long id);

    void deleteUser(Long id);

    List<User> getUsers();

}
