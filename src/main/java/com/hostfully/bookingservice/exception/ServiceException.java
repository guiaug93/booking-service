package com.hostfully.bookingservice.exception;

import org.springframework.http.HttpStatus;

public class ServiceException extends RuntimeException {
    private final HttpStatus httpStatus;

    public ServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}