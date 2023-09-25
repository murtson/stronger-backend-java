package com.pmstudios.stronger.auth;

import com.pmstudios.stronger.auth.dto.AuthResponse;
import com.pmstudios.stronger.auth.dto.LoginRequest;
import com.pmstudios.stronger.security.SecurityConstants;
import com.pmstudios.stronger.token.JwtService;
import com.pmstudios.stronger.token.RefreshToken;
import com.pmstudios.stronger.token.RefreshTokenRepository;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import com.pmstudios.stronger.userRole.UserRole;
import com.pmstudios.stronger.userRole.UserRoleEnum;
import com.pmstudios.stronger.userRole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRole role = userRoleRepository.findByName(UserRoleEnum.USER.toString()).orElseThrow(NoSuchElementException::new);
        user.setRoles(Collections.singletonList(role));

        String token = jwtService.generateRefreshToken(user);
        RefreshToken refreshToken = createRefreshToken(user, token);
        user.setRefreshToken(refreshToken);

        return userService.saveUser(user);
    }

    @Override
    public User loginUser(LoginRequest loginRequest) {
        // The authentication manager will throw an exception if the email or password is wrong
        // see CustomAuthenticationManager to see validation logic
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getUserByEmail(loginRequest.getEmail());

        String refreshToken = jwtService.generateRefreshToken(user);
        RefreshToken newRefreshToken = createRefreshToken(user, refreshToken);

        // delete the previous refresh token
        user.setRefreshToken(newRefreshToken);
        return userService.saveUser(user);
    }

    @Override
    public RefreshToken saveRefreshToken(User user, String refreshToken) {
        RefreshToken token = createRefreshToken(user, refreshToken);
        return refreshTokenRepository.save(token);
    }

    @Override
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // no, this should be extracted from a cookie
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION); // Bearer RefreshToken
        final String refreshToken;

        boolean noTokenInHeader = authHeader == null || !authHeader.startsWith(SecurityConstants.BEARER);
        if (noTokenInHeader) { // no token in header, send to next filter which is AuthenticationFilter (UsernamePasswordAuthenticationFilter)
            return;
        }

        String accessToken = authHeader.replace(SecurityConstants.BEARER, "");
        String userEmail = jwtService.getUsernameFromAccessToken(accessToken);

        boolean userNotAuthenticated = userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
        if (userNotAuthenticated) {
            User user = (User) userService.loadUserByUsername(userEmail);
            if (jwtService.isAccessTokenValid(accessToken, user)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                System.out.println("Token for: " + userEmail + " was not valid");
            }
        }
    }

    private RefreshToken createRefreshToken(User user, String refreshToken) {
        Date expiration = jwtService.getExpirationFromRefreshToken(refreshToken);
        LocalDateTime tokenExpiration = LocalDateTime.ofInstant(expiration.toInstant(), ZoneId.systemDefault());

        return RefreshToken.builder()
                .id(null)
                .token(refreshToken)
                .createdAt(LocalDateTime.now())
                .expiresAt(tokenExpiration)
                .revoked(false)
                .expired(false)
                .user(user)
                .build();
    }


}
