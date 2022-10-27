package com.jungeeks.accounts.controller;

import com.jungeeks.accounts.exception.InvalidRequestException;
import com.jungeeks.accounts.exception.PathNotFoundException;
import com.jungeeks.accounts.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(UserNotFoundException userNotFoundException){
        log.warn(String.format("Bad request by %s", userNotFoundException.getMessage()));
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PathNotFoundException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(PathNotFoundException pathNotFoundException){
        log.warn(String.format("Bad request by %s", pathNotFoundException.getMessage()));
        return new ResponseEntity<>(pathNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(InvalidRequestException invalidRequestException){
        log.warn(String.format("Bad request by %s", invalidRequestException.getMessage()));
        return new ResponseEntity<>(invalidRequestException.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
