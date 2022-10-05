package com.pmstudios.stronger.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "logged_exercise")
public class LoggedExercise {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "weight")
    private float weight;

    @Column(name = "reps")
    private int reps;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private Exercise exercise;

    @ManyToOne(optional = false)
    @JoinColumn(name = "logged_workout_set_id", referencedColumnName = "id")
    private LoggedWorkoutSet loggedWorkoutSet;

}
