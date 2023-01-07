package com.pmstudios.stronger.loggedExercise;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.loggedSet.dto.LoggedSetDto;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.exercise.Exercise;
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

    @JsonIgnoreProperties(value = {"loggedExercises", "user"})
    @ManyToOne(optional = false)
    @JoinColumn(name = "workout_id", referencedColumnName = "id")
    private Workout workout;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private Exercise exercise;

    @JsonIgnoreProperties(value = {"loggedExercise"})
    @OneToMany(mappedBy = "loggedExercise", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LoggedSet> loggedSets;

}
