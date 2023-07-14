package com.pmstudios.stronger.auth;

import com.pmstudios.stronger.auth.dto.AuthResponse;
import com.pmstudios.stronger.auth.dto.LoginRequest;
import com.pmstudios.stronger.auth.dto.RegisterRequest;
import com.pmstudios.stronger.token.JwtService;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    private final JwtService jwtService;

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody @Valid RegisterRequest registerRequest, HttpServletResponse response) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            String message = "Email already exists in our records";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        User user = RegisterRequest.toEntity(registerRequest);
        User createdUser = authService.registerUser(user);

        String refreshToken = jwtService.generateRefreshToken(createdUser);
        authService.saveRefreshToken(createdUser, refreshToken);

        // Set refresh_token cookie
        Cookie cookie = generateRefreshTokenCookie(refreshToken);
        response.addCookie(cookie);

        String accessToken = jwtService.generateAccessToken(createdUser);
        AuthResponse responseBody = AuthResponse.from(accessToken, createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Validated @RequestBody @Valid LoginRequest loginRequest) {

        // the authentication manager will throw an exception if the email or password is wrong
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getUserByEmail(loginRequest.getEmail());
        String accessToken = jwtService.generateAccessToken(user);

        AuthResponse responseBody = AuthResponse.from(accessToken, user);

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    private Cookie generateRefreshTokenCookie(String refreshToken) {
        long refreshTokenExpirationInMs = jwtService.getRefreshTokenExpiration();
        int refreshTokenExpirationInSeconds = (int) refreshTokenExpirationInMs / 1000;

        Cookie cookie = new Cookie("refresh_token", refreshToken);
        cookie.setMaxAge(refreshTokenExpirationInSeconds); // Set cookie expiry time (in seconds), if not provided (or set to -1) it will be treated as a session cookie and deleted once the browser closes
        cookie.setHttpOnly(true); // Make the cookie accessible only via HTTP, not JavaScript
        cookie.setSecure(true); // Make the cookie secure (works only with HTTPS)
        cookie.setPath("/"); // Global cookie accessible everywhere
        return cookie;
    }
}
