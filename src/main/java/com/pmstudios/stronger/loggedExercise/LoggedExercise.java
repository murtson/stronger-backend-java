package com.pmstudios.stronger.loggedExercise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.exercise.Exercise;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
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

    @Column(name = "exercise_top_set")
    private BigDecimal exerciseTopSet;

    @JsonIgnoreProperties(value = { "loggedExercise" })
    @OneToMany(mappedBy = "loggedExercise", cascade = CascadeType.ALL)
    private List<LoggedSet> loggedSets;

    @JsonIgnoreProperties(value = { "loggedExercises" })
    @ManyToOne(optional = false)
    @JoinColumn(name = "workout_id", referencedColumnName = "id")
    private Workout workout;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private Exercise exercise;


}