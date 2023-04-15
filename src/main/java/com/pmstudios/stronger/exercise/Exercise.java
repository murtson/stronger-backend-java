package com.pmstudios.stronger.exercise;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategory;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // unique: there cannot be exercises with the same name
    @NotBlank(message = "exercise name must not be blank")
    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @NonNull
    @JsonIgnoreProperties(value = { "exercises" })
    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_category_id", referencedColumnName = "id")
    private ExerciseCategory exerciseCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<LoggedExercise> loggedExercises;

    @JsonIgnore
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private Set<ExercisePr> exercisePR;

//    @JsonIgnoreProperties(value = {"exericse"})
//    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
//    @JoinColumn(name = "exercise_pr_id", referencedColumnName = "id")
//    private ExercisePr exercisePR;

}
