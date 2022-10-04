package com.pmstudios.stronger.pojo;


import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "exercise_category")
public class ExerciseCategory {
    public enum MuscleCategory {
        CHEST,
        TRICEPS,
        SHOULDERS,
        BACK,
        BICEPS,
        LEGS,
        ABS
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name")
    private MuscleCategory name;

}
