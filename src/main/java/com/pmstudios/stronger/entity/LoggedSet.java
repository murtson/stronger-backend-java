package com.pmstudios.stronger.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "logged_set")
public class LoggedSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "weight")
    private float weight;

    @NonNull
    @Column(name = "reps")
    private int reps;

    @JsonIgnoreProperties(value = { "loggedSets" })
    @ManyToOne(optional = false)
    @JoinColumn(name = "logged_exercise_id", referencedColumnName = "id")
    private LoggedExercise loggedExercise;

}
