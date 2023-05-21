package com.pmstudios.stronger.user.dto;

import com.pmstudios.stronger.user.User;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "username is required")
    private String username;

    @NotBlank(message = "firstName is required")
    private String firstName;

    @NotBlank(message = "lastName is required")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "password is required")
    private String password;

    public static User toEntity(RegisterRequest dto) {
        return new User(dto.getUsername(), dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getPassword());
    }

}