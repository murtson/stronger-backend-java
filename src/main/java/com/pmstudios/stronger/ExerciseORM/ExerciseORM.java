package com.pmstudios.stronger.ExerciseORM;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exercise_ORM")
public class ExerciseORM {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NonNull
    @Column(name = "weight")
    private BigDecimal weight;

    @NonNull
    @Column(name = "reps")
    private int reps;

    @NonNull
    @Column(name = "estimated_one_rep_max")
    private BigDecimal estimatedOneRepMax;


}
