package com.pmstudios.stronger.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy hh:mm:ss")
    private LocalDateTime timestamp;
    private List<String> messages;

    public ErrorResponse(List<String> messages) {
        this.messages = messages;
        this.timestamp = LocalDateTime.now();
    }
}
