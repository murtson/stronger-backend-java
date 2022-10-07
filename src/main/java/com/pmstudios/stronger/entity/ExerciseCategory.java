package com.pmstudios.stronger.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
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

    @NonNull
    @Column(name = "name")
    private MuscleCategory name;

    @JsonIgnoreProperties(value = { "exerciseCategory" })
    @OneToMany(mappedBy = "exerciseCategory", cascade = CascadeType.ALL)
    private List<Exercise> exercises;

}
