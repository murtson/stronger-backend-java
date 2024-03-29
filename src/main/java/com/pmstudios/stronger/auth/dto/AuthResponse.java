package com.pmstudios.stronger.auth.dto;

import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.userRole.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String username;
    private String email;
    private String accessToken;
    private Long userId;
    private List<String> roles;

    public static AuthResponse from(String token, User user) {
        return AuthResponse.builder()
                .accessToken(token)
                .userId(user.getId())
                .username(user.getUsername())
                .roles(user.getRoles().stream().map(UserRole::getName).toList())
                .email(user.getEmail())
                .build();
    }
}
