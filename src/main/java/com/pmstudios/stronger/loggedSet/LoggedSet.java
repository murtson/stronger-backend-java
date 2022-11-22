package com.pmstudios.stronger.loggedSet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

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

    // TODO: research on how to validate float
//    @DecimalMin(value = "0", inclusive = true)
//    @DecimalMax(value = "10.0", inclusive = true)
    @NonNull
    @Column(name = "weight")
    private BigDecimal weight;

    // TODO: research on how to validate int
    @Min(value = 0L, message = "reps must be positive")
    @NonNull
    @Column(name = "reps")
    private int reps;

    // TODO: research on how to validate float
    @NonNull
    @Column(name = "estimated_one_rep_max")
    private BigDecimal estimatedOneRepMax;

    @JsonIgnoreProperties(value = { "loggedSets" })
    @ManyToOne(optional = false)
    @JoinColumn(name = "logged_exercise_id", referencedColumnName = "id")
    private LoggedExercise loggedExercise;


}
