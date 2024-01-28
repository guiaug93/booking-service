package com.hostfully.bookingservice.exception;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<Object> handleServiceException(ServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getHttpStatus(), ex.getMessage());
        return new ResponseEntity<>(errorResponse, ex.getHttpStatus());
    }
}