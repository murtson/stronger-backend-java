package com.pmstudios.stronger.user.dto;

import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.userRole.UserRoleEnum;

public class UserUtils {

    public static boolean isAdminUser(User user) {
        return user.getRoles().stream().anyMatch(userRole ->
                userRole.getName().equals(UserRoleEnum.ADMIN.toString())
        );
    }

    public static boolean isStandardUser(User user) {
        return user.getRoles().stream().anyMatch(userRole ->
                userRole.getName().equals(UserRoleEnum.USER.toString())
        );
    }

}
