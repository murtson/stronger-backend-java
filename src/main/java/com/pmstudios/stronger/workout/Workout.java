package com.pmstudios.stronger.workout;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    // TODO: create validator for date
    @NotNull(message = "startDate must not be null")
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "complete_date")
    private LocalDateTime completeDate;

    // TODO: create validator for enum
    @NotNull(message = "workoutStatus must not be null")
    @Column(name = "status")
    private WorkoutStatus workoutStatus;

    @JsonIgnoreProperties(value = { "workouts" })
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonIgnoreProperties(value = { "workout" })
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL)
    private List<LoggedExercise> loggedExercises;

}
