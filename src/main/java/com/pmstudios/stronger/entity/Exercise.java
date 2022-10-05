package com.pmstudios.stronger.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "exercise")
public class Exercise {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_category_id", referencedColumnName = "id")
    private ExerciseCategory exerciseCategory;

    // probably not have CascadeType.ALL
    @OneToMany(mappedBy = "exericse", cascade = CascadeType.ALL)
    private List<LoggedExercise> loggedExercises;

}
