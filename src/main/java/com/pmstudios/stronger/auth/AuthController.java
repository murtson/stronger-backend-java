package com.pmstudios.stronger.auth;

import com.pmstudios.stronger.auth.dto.AuthResponse;
import com.pmstudios.stronger.auth.dto.LoginRequest;
import com.pmstudios.stronger.auth.dto.RegisterRequest;
import com.pmstudios.stronger.token.JwtService;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthService authService;
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody @Valid RegisterRequest registerRequest, HttpServletResponse response) {
        if (userService.existsByEmail(registerRequest.getEmail())) {
            logger.warn("Email {} already exists in our records", registerRequest.getEmail());
            String message = "Email already exists in our records";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        User user = RegisterRequest.toEntity(registerRequest);
        User createdUser = authService.registerUser(user);

        // Set refresh token cookie in response
        String refreshToken = createdUser.getRefreshToken().getToken();
        setRefreshTokenCookie(refreshToken, response);

        String accessToken = jwtService.generateAccessToken(createdUser);
        AuthResponse responseBody = AuthResponse.from(accessToken, createdUser);

        logger.info("User registration successful for {}", createdUser.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseBody);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginUser(@Validated @RequestBody @Valid LoginRequest loginRequest, HttpServletResponse response) {

        User updatedUser = authService.loginUser(loginRequest);

        // Set refresh token cookie in response
        String refreshToken = updatedUser.getRefreshToken().getToken();
        setRefreshTokenCookie(refreshToken, response);

        String accessToken = jwtService.generateAccessToken(updatedUser);
        AuthResponse responseBody = AuthResponse.from(accessToken, updatedUser);

        logger.info("User login successful for {}", updatedUser.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletRequest request, HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();
        String message = "You are already signed out";

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("REFRESH_TOKEN")) {
                String token = cookie.getValue();
                String userEmail = jwtService.getUsernameFromRefreshToken(token);
                message = "User " + userEmail + " signed out";
                removeRefreshTokenCookie(response);
            }
        }

        logger.info((message));

        return ResponseEntity.ok("You successfully signed out");
    }

    @GetMapping("/token/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // need to check that the refresh token is valid, later on in DB as well
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            String message = "No cookie found in refresh token request";
            logger.error(message);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("REFRESH_TOKEN")) {
                String token = cookie.getValue();
                String userEmail = jwtService.getUsernameFromRefreshToken(token);
                User user = (User) userService.loadUserByUsername(userEmail);

                boolean isValid = jwtService.isRefreshTokenValid(token, user);
                if (!isValid) {
                    String message = "Refresh token NOT valid";
                    logger.warn(message);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
                }
                logger.info("Refresh token valid!");
                String accessToken = jwtService.generateAccessToken(user);
                AuthResponse responseBody = AuthResponse.from(accessToken, user);

                return ResponseEntity.status(HttpStatus.OK).body(responseBody);
            }
        }

        String message = "Refresh token cookie not found!";
        logger.error(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);

    }

    private void setRefreshTokenCookie(String refreshToken, HttpServletResponse response) {
        long refreshTokenExpirationInMs = jwtService.getRefreshTokenExpiration();
        int refreshTokenExpirationInSeconds = (int) refreshTokenExpirationInMs / 1000;

        ResponseCookie cookie = ResponseCookie.from("REFRESH_TOKEN", refreshToken)
                .maxAge(refreshTokenExpirationInSeconds) // Set cookie expiry time (in seconds), if not provided (or set to -1) it will be treated as a session cookie and deleted once the browser closes
                .path("/") // Global cookie accessible everywhere
                .secure(true) // Make the cookie secure (works only with HTTPS)
                .httpOnly(true) // Make the cookie accessible only via HTTP, not JavaScript
                .sameSite("None") // Set SameSite to None
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void removeRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("REFRESH_TOKEN", "")
                .maxAge(0) // Set max age to 0 to delete the cookie
                .path("/") // Ensure the path matches the path of the cookie you want to delete
                .secure(true) // Ensure secure flag matches the original cookie settings
                .httpOnly(true) // Ensure httpOnly flag matches the original cookie settings
                .sameSite("None") // Ensure SameSite attribute matches the original cookie settings
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }
}
