package com.example.medicalshop.exceptions;

import org.springframework.http.HttpStatus;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, cause);
    }

    public BadRequestException(Throwable cause) {
        super(cause);
    }
}
