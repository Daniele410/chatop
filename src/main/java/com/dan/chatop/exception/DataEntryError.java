package com.dan.chatop.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(value = BAD_REQUEST, reason = "Fields should not be empty!")
public class DataEntryError extends Throwable {
    public DataEntryError(String message) {
        super(message);
    }
}
