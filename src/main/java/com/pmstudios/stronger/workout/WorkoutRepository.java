package com.pmstudios.stronger.workout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {
    List<Workout> findByUserId(Long userId);

    List<Workout> findAllByStartDateBetweenAndUserId(LocalDateTime fromDate, LocalDateTime toDate, Long userId);

    Optional<Workout> findByStartDateAndUserId(LocalDateTime date, Long userId);

}
