package com.dan.chatop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(reason = "Invalid image format", value = HttpStatus.BAD_REQUEST)
public class InvalidImageFormatException extends Throwable {
    public InvalidImageFormatException(String message) {
        super(message);
    }
}
