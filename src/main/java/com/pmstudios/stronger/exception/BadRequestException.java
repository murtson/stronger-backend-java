package com.pmstudios.stronger.exception;


import java.time.LocalDate;

public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }


}
