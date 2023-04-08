package com.pmstudios.stronger.exercisePr;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exercise_pr", uniqueConstraints = {@UniqueConstraint(name = "UniqueUserExerciseAndReps", columnNames = {"reps", "user_id", "exercise_id"})})
public class ExercisePr {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NonNull
    @Column(name = "weight")
    private Double weight;

    @NonNull
    @Column(name = "reps")
    private Integer reps;

    @NonNull
    @Column(name = "estimated_one_rep_max")
    private Double estimatedOneRepMax;

    @NonNull
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NonNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_id", referencedColumnName = "id")
    private Exercise exercise;

    @NonNull
    @JsonIgnoreProperties(value = { "loggedExercise" })
    @OneToOne(optional = false)
    @JoinColumn(name = "exercise_pr_id", referencedColumnName = "id")
    private LoggedSet loggedSet;

    public static ExercisePr from(LoggedSet loggedSet) {
        return new ExercisePr(
                loggedSet.getWeight(), loggedSet.getReps(),
                loggedSet.getEstimatedOneRepMax(), loggedSet.getLoggedExercise().getWorkout().getUser(),
                loggedSet.getLoggedExercise().getExercise(), loggedSet);
    }

}
