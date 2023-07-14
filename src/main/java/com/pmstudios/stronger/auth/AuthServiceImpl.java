package com.pmstudios.stronger.auth;

import com.pmstudios.stronger.token.JwtService;
import com.pmstudios.stronger.token.RefreshToken;
import com.pmstudios.stronger.token.RefreshTokenRepository;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.user.UserService;
import com.pmstudios.stronger.userRole.UserRole;
import com.pmstudios.stronger.userRole.UserRoleEnum;
import com.pmstudios.stronger.userRole.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    @Override
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        UserRole role = userRoleRepository.findByName(UserRoleEnum.USER.toString()).orElseThrow(NoSuchElementException::new);
        user.setRoles(Collections.singletonList(role));

        return userService.saveUser(user);
    }

    @Override
    public RefreshToken saveRefreshToken(User user, String refreshToken) {
        RefreshToken token = createRefreshToken(user, refreshToken);
        return refreshTokenRepository.save(token);
    }

    private RefreshToken createRefreshToken(User user, String refreshToken) {
        Date expiration = jwtService.getExpirationFromRefreshToken(refreshToken);
        LocalDateTime tokenExpiration = LocalDateTime.now().plus(expiration.getTime(), ChronoUnit.MILLIS);

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
