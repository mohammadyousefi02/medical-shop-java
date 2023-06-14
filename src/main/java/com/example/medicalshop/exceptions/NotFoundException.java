package com.example.medicalshop.exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends Exception {


    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, HttpStatus.NOT_FOUND, cause);
    }

    public NotFoundException(Throwable cause) {
        super(cause);
    }
}
