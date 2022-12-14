package com.pmstudios.stronger.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.workout.Workout;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @NotBlank(message = "password must not be blank")
    @NonNull
    @Column(name = "password", nullable = false)
    private String password;

    @JsonIgnoreProperties(value = { "user" })
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Workout> workouts;

    @JsonIgnoreProperties(value = { "user" })
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ExercisePr> exercisePersonalRecords;
}