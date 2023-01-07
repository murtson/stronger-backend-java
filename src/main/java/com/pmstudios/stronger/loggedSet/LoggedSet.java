package com.pmstudios.stronger.loggedSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;

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
    private Double weight;

    @Min(value = 0L, message = "reps must be positive")
    @NonNull
    @Column(name = "reps")
    private Integer reps;

    @NonNull
    @Column(name = "estimated_one_rep_max")
    private Double estimatedOneRepMax;

    @NonNull
    @Column(name = "is_best_set")
    private boolean isBestSet;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "logged_exercise_id", referencedColumnName = "id")
    private LoggedExercise loggedExercise;

    // TODO: perhaps have a join table instead (to remove NULL-values)
    @JsonIgnoreProperties(value = { "loggedSet" })
    @OneToOne(mappedBy = "loggedSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExercisePr exercisePr;

}
