package com.pmstudios.stronger.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.token.RefreshToken;
import com.pmstudios.stronger.userRole.UserRole;
import com.pmstudios.stronger.workout.Workout;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "_user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "username cannot be blank")
    @NonNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotBlank(message = "first name must not be blank")
    @NonNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotBlank(message = "first name must not be blank")
    @NonNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotBlank(message = "email must not be blank")
    @NonNull
    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @NotBlank(message = "password must not be blank")
    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<UserRole> roles = new ArrayList<>();

    @JsonIgnoreProperties(value = {"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Workout> workouts;

    @JsonIgnoreProperties(value = {"user"})
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ExercisePr> exercisePersonalRecords;

    @JsonIgnoreProperties(value = {"user"})
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private RefreshToken refreshToken;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}