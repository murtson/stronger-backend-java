package com.pmstudios.stronger.workout;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface WorkoutService {

    Workout getWorkoutById(Long id);

    Workout saveWorkout(Workout workout);

    Workout completeWorkout(Long id);

    void deleteWorkoutById(Long id);

    List<Workout> getAllWorkouts();

    List<Workout> getWorkoutsByUserId(Long userId);

    List<Workout> getUserWorkoutsBetweenDates(LocalDate startDate, LocalDate endDate, Long userId);

    Optional<Workout> getWorkoutByDateAndUserId(LocalDate date, Long userId);


    Workout createWorkout(Workout workout, LocalDateTime startDateTime, Long userId);

}
