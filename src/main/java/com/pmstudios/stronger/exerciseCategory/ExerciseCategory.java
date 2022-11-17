package com.pmstudios.stronger.exerciseCategory;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.exercise.Exercise;
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

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", unique = true)
    private MuscleCategory name;

    @JsonIgnoreProperties(value = { "exerciseCategory" })
    @OneToMany(mappedBy = "exerciseCategory", cascade = CascadeType.ALL)
    private List<Exercise> exercises;

}
