package com.dan.chatop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RentalNotFondException extends Throwable {
    public RentalNotFondException(String message) {
        super(message);
    }
}
