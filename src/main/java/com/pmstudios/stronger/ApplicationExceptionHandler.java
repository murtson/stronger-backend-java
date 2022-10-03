package com.pmstudios.stronger;

import com.pmstudios.stronger.exception.ErrorResponse;
import com.pmstudios.stronger.exception.ExerciseNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ExerciseNotFoundException.class)
    public ResponseEntity<Object> handleExerciseNotFoundException(ExerciseNotFoundException ex) {

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

}
