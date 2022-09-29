package com.jungeeks.controller;

import com.jungeeks.exception.PathNotFoundException;
import com.jungeeks.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.FileNotFoundException;

@ControllerAdvice
@Log4j2
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

}
