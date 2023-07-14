package com.pmstudios.stronger.user;

import com.pmstudios.stronger.auth.dto.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId, @AuthenticationPrincipal User authUser) {

        if (!Objects.equals(authUser.getId(), userId) && !UserUtils.isAdminUser(authUser)) {
            String message = "You are not allowed to delete other users";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(message);
        }

        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body("You deleted user with userId: " + userId);
    }


}
