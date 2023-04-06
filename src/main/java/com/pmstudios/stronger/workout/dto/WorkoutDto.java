package com.pmstudios.stronger.workout.dto;

import com.pmstudios.stronger.loggedExercise.dto.LoggedExerciseDto;
import com.pmstudios.stronger.workout.WorkoutStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class WorkoutDto {


    private Long id;


    private String name;


    private LocalDateTime startDate;


    private LocalDateTime completeDate;


    private WorkoutStatus workoutStatus;


    private Long userId;


    private List<LoggedExerciseDto> loggedExercises;
}
