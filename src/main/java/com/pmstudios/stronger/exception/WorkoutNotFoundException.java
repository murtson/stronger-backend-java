package com.pmstudios.stronger.exception;

import java.time.LocalDate;

public class WorkoutNotFoundException extends RuntimeException {

    public WorkoutNotFoundException(Long id) {
        super("The workout id '" + id + "' does not exist in our records");
    }

    public WorkoutNotFoundException(LocalDate date, Long userId) {
        super(String.format("No workout found for date: %s for user: %s", date.toString(), userId));
    }
}
