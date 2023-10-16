package com.pmstudios.stronger.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {

    // without JsonFormat, we would get "2022-11-17T12:53:05.2617804"
    // now we get 2022-11-17 12:53:05 instead
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
    private List<String> messages;

    public ErrorResponse(List<String> messages) {
        this.messages = messages;
        this.timestamp = LocalDateTime.now();
    }
}
