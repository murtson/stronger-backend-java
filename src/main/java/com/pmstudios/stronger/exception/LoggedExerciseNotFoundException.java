package com.pmstudios.stronger.exception;

public class LoggedExerciseNotFoundException extends RuntimeException {

    public LoggedExerciseNotFoundException(Long id) {
        super("The logged exercise id '" + id + "' does not exist in our records");
    }
}

