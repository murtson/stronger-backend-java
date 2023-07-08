package com.pmstudios.stronger.user;

import com.pmstudios.stronger.security.JwtService;
import com.pmstudios.stronger.user.dto.AuthResponse;
import com.pmstudios.stronger.user.dto.LoginRequest;
import com.pmstudios.stronger.user.dto.RegisterRequest;
import com.pmstudios.stronger.user.dto.UserUtils;
import com.pmstudios.stronger.userRole.UserRole;
import com.pmstudios.stronger.userRole.UserRoleEnum;
import com.pmstudios.stronger.userRole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final JwtService jwtService;

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


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody @Valid RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            String message = "Email already exists in our records";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        User user = RegisterRequest.toEntity(registerRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRole role = userRoleRepository.findByName(UserRoleEnum.USER.toString()).orElseThrow(NoSuchElementException::new);
        user.setRoles(Collections.singletonList(role));

        User createdUser = userService.saveUser(user);
        String jwtToken = jwtService.generateToken(createdUser);

        AuthResponse response = AuthResponse.from(jwtToken, createdUser);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Validated @RequestBody @Valid LoginRequest loginRequest) {

        // the authentication manager will throw an exception if the email or password is wrong
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getUserByEmail(loginRequest.getEmail());
        String jwtToken = jwtService.generateToken(user);

        AuthResponse response = AuthResponse.from(jwtToken, user);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
