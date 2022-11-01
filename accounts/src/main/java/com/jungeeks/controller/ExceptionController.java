package com.jungeeks.controller;

import com.jungeeks.exception.InvalidRequestException;
import com.jungeeks.exception.PathNotFoundException;
import com.jungeeks.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(UserNotFoundException userNotFoundException){
        log.warn("Bad request by {}", userNotFoundException.getMessage());
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PathNotFoundException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(PathNotFoundException pathNotFoundException){
        log.warn("Bad request by {}", pathNotFoundException.getMessage());
        return new ResponseEntity<>(pathNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(InvalidRequestException invalidRequestException){
        log.warn("Bad request by {}", invalidRequestException.getMessage());
        return new ResponseEntity<>(invalidRequestException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
