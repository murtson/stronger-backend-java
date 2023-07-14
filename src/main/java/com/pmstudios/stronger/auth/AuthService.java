package com.pmstudios.stronger.auth;

import com.pmstudios.stronger.token.RefreshToken;
import com.pmstudios.stronger.user.User;

public interface AuthService {

    User registerUser(User user);

    RefreshToken saveRefreshToken(User user, String token);

}
