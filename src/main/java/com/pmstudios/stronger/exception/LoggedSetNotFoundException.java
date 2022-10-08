package com.pmstudios.stronger.exception;

public class LoggedSetNotFoundException extends RuntimeException {

    public LoggedSetNotFoundException(Long id) {
        super("The logged set id '" + id + "' does not exist in our records");
    }
}
