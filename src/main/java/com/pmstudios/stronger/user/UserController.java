package com.pmstudios.stronger.user;

import com.pmstudios.stronger.security.JwtService;
import com.pmstudios.stronger.user.dto.AuthResponse;
import com.pmstudios.stronger.user.dto.LoginRequest;
import com.pmstudios.stronger.user.dto.RegisterRequest;
import com.pmstudios.stronger.user.dto.UserMapper;
import com.pmstudios.stronger.userRole.UserRole;
import com.pmstudios.stronger.userRole.UserRoleEnum;
import com.pmstudios.stronger.userRole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRoleRepository userRoleRepository;

    private final JwtService jwtService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@Validated @RequestBody @Valid RegisterRequest registerRequest) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            return new ResponseEntity<>(AuthResponse.builder().build(), HttpStatus.BAD_REQUEST);
        }

        User user = userMapper.registerDtoToEntity(registerRequest);
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
