package com.pmstudios.stronger.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @JsonIgnoreProperties(value = { "loggedExercise" })
    @OneToMany(mappedBy = "loggedExercise")
    private List<LoggedSet> loggedSets;

    @JsonIgnoreProperties(value = { "loggedExercises" })
    @ManyToOne(optional = false)
    @JoinColumn(name = "workout_id", referencedColumnName = "id")
    private Workout workout;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private Exercise exercise;


}
