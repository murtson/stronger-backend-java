package com.pmstudios.stronger.user.dto;

import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.userRole.UserRoleEnum;

public class UserUtils {

    public static User registerRequestToEntity(RegisterRequest dto) {
        return new User(dto.getUsername(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword());
    }

    public static boolean isAdminUser(User user) {
        return user.getRoles().stream().anyMatch(userRole ->
                userRole.getName().equals(UserRoleEnum.ADMIN.toString())
        );
    }

    public static boolean isUser(User user) {
        return user.getRoles().stream().anyMatch(userRole ->
                userRole.getName().equals(UserRoleEnum.USER.toString())
        );
    }

}
