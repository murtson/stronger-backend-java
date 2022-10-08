package com.pmstudios.stronger.exception;

public class ExerciseCategoryNotFoundException extends RuntimeException {

    public ExerciseCategoryNotFoundException(Long id) {
        super("The category exercise id '" + id + "' does not exist in our records");
    }
}
