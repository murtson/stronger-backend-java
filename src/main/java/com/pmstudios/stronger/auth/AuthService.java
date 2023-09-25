package com.pmstudios.stronger.auth;

import com.pmstudios.stronger.auth.dto.AuthResponse;
import com.pmstudios.stronger.auth.dto.LoginRequest;
import com.pmstudios.stronger.token.RefreshToken;
import com.pmstudios.stronger.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    User registerUser(User user);

    User loginUser(LoginRequest loginRequest);

    RefreshToken saveRefreshToken(User user, String token);

    void refreshToken(HttpServletRequest request, HttpServletResponse response);
}
