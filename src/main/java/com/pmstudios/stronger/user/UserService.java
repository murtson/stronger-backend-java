package com.pmstudios.stronger.user;

import java.util.List;


public interface UserService {

    User getUser(Long id);
    User saveUser(User user);
    void deleteUser(Long id);
    List<User> getUsers();
    User autheticateUser(User user);
}
