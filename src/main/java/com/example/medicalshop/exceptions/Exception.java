package com.example.medicalshop.exceptions;

import org.springframework.http.HttpStatus;

public abstract class Exception extends RuntimeException {

    private HttpStatus httpStatus;

    public Exception(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public Exception(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public Exception(Throwable cause) {
        super(cause);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
