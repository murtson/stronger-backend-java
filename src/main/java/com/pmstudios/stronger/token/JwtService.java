package com.pmstudios.stronger.token;

import com.pmstudios.stronger.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.access-token-key}")
    private String accessTokenSecretKey;

    @Value("${application.security.jwt.refresh-token-key}")
    private String refreshTokenSecretKey;

    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;


    // --------------- Public Methods --------------- //

    public String generateAccessToken(User user) {
        Key secretKey = getAccessTokenSecretKey();
        return generateToken(secretKey, user, accessTokenExpiration);
    }

    public String generateRefreshToken(User user) {
        Key secretKey = getRefreshTokenSecretKey();
        return generateToken(secretKey, user, refreshTokenExpiration);
    }

    public String getUsernameFromAccessToken(String accessToken) {
        Key secretKey = getAccessTokenSecretKey();
        return extractUsername(accessToken, secretKey);
    }

    public String getUsernameFromRefreshToken(String refreshToken) {
        Key secretKey = getRefreshTokenSecretKey();
        return extractUsername(refreshToken, secretKey);
    }

    public boolean isAccessTokenValid(String accessToken, User user) {
        Key secretKey = getAccessTokenSecretKey();
        return isTokenValid(accessToken, secretKey, user);
    }

    public boolean isRefreshTokenValid(String refreshToken, User user) {
        Key secretKey = getRefreshTokenSecretKey();
        return isTokenValid(refreshToken, secretKey, user);
    }

    public Date getExpirationFromRefreshToken(String refreshToken) {
        Key secretKey = getRefreshTokenSecretKey();
        return extractExpiration(refreshToken, secretKey);
    }

    public long getRefreshTokenExpiration() {
        return refreshTokenExpiration;
    }

    // --------------- Private Methods --------------- //

    private Key getAccessTokenSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getRefreshTokenSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(refreshTokenSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Claims extractAllClaims(String token, Key secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String generateToken(Key secretKey, User user, long tokenExpiration) {
        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // --------------- Common Methods --------------- //

    private String extractUsername(String token, Key secretKey) {
        return extractClaim(token, secretKey, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Key secretKey, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, secretKey);
        return claimsResolver.apply(claims);
    }

    private boolean isTokenValid(String token, Key secretKey, User user) {
        final String username = extractUsername(token, secretKey);
        return username.equals(user.getEmail()) && !isTokenExpired(token, secretKey);
    }

    private boolean isTokenExpired(String token, Key secretKey) {
        return extractExpiration(token, secretKey).before(new Date());
    }

    private Date extractExpiration(String token, Key secretKey) {
        return extractClaim(token, secretKey, Claims::getExpiration);
    }
}
