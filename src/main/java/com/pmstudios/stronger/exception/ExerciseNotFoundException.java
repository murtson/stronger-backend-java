package com.pmstudios.stronger.exception;

public class ExerciseNotFoundException extends RuntimeException {

    public ExerciseNotFoundException(Long id) {
        super("The exercise id '" + id + "' does not exist in our records");
    }
}
