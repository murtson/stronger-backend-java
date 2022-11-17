package com.pmstudios.stronger.exercise;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategory;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
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

    // unique: there cannot be exercises with the same name
    @NotBlank(message = "exercise name must not be blank")
    @NonNull
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonIgnoreProperties(value = { "exercises" })
    @ManyToOne(optional = false)
    @JoinColumn(name = "exercise_category_id", referencedColumnName = "id")
    private ExerciseCategory exerciseCategory;

    @JsonIgnore
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    private List<LoggedExercise> loggedExercises;

}
