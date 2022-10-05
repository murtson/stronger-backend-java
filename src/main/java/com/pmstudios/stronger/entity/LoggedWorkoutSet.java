package com.pmstudios.stronger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "logged_workout_set")
public class LoggedWorkoutSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_workout_id", referencedColumnName = "id")
    private UserWorkout userWorkout;

    @JsonIgnore
    @OneToMany(mappedBy = "loggedWorkoutSet")
    private List<LoggedExercise> loggedExercises;



}
