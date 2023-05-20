package com.pmstudios.stronger.security;

import com.auth0.jwt.algorithms.Algorithm;

public class SecurityConstants {

    public static final String REGISTER_PATH = "/user/register";
    public static final String LOGIN_PATH = "/user/login";
    public static final int TOKEN_EXPIRATION_TIME = 1000 * 60 * 24; // 24 hours in milliseconds
    public static final String SECRET_KEY = "7234743777217A25432A462D4A614E645267556B58703273357638792F413F4428472B4B6250655368566D597133743677397A244326452948404D635166546A";
    public static final String AUTHORIZATION = "Authorization"; // Authorization: "Bearer + Token"
    public static final String BEARER = "Bearer "; // Authorization: "Bearer + Token"
    public static final Algorithm hashAlgorithm = Algorithm.HMAC512(SecurityConstants.SECRET_KEY);

}
