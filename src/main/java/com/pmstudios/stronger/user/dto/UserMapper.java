package com.pmstudios.stronger.user.dto;

import com.pmstudios.stronger.user.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User registerDtoToEntity(RegisterRequest dto) {
        return new User(dto.getUsername(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword());
    }

}
