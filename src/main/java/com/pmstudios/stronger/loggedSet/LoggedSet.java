package com.pmstudios.stronger.loggedSet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "logged_set", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "logged_exercise_id", "is_top_logged_set" })
})
public class LoggedSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @NotNull(message = "weight cannot be null")
    @Column(name = "weight")
    private Double weight;

    @Min(value = 0L, message = "reps must be positive")
    @NonNull
    @NotNull(message = "reps cannot be null")
    @Column(name = "reps")
    private Integer reps;

    @NonNull
    @NotNull(message = "estimatedOneRepMax cannot be null")
    @Column(name = "estimated_one_rep_max")
    private Double estimatedOneRepMax;

    @NonNull
    @NotNull(message = "isTopLoggedSet cannot be null")
    @Column(name = "is_top_logged_set")
    private boolean isTopLoggedSet;


    @NotNull(message = "loggedExercise cannot be null")
    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "logged_exercise_id", referencedColumnName = "id")
    private LoggedExercise loggedExercise;

    // TODO: perhaps have a join table instead (to remove NULL-values)
    @JsonIgnoreProperties(value = { "loggedSet" })
    @OneToOne(mappedBy = "loggedSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private ExercisePr exercisePr;

}
