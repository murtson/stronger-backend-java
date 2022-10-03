package com.pmstudios.stronger.exception;

public class ExerciseNotFoundException extends RuntimeException {

    public ExerciseNotFoundException(int id) {
        super("The id '" + id + "' does not exist in our records");
    }
}
