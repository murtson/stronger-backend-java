package com.pmstudios.stronger.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;

public class SecurityConstants {

    public static final String REGISTER_PATH = "/auth/register";
    public static final String LOGIN_PATH = "/auth/login";
    public static final int TOKEN_EXPIRATION_TIME = 1000 * 60 * 60 * 24; // 24 hours in milliseconds
    public static final String AUTHORIZATION = "Authorization"; // Authorization: "Bearer + RefreshToken"
    public static final String BEARER = "Bearer "; // Authorization: "Bearer + RefreshToken"

}
