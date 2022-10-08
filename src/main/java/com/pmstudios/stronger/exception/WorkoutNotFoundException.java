package com.pmstudios.stronger.exception;

public class WorkoutNotFoundException extends RuntimeException {

    public WorkoutNotFoundException(Long id) {
        super("The workout id '" + id + "' does not exist in our records");
    }
}
