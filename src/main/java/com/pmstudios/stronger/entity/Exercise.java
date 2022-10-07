package com.pmstudios.stronger.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name = "exercise")
public class Exercise {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_category_id", referencedColumnName = "id")
    private ExerciseCategory exerciseCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<LoggedExercise> loggedExercises;

}
