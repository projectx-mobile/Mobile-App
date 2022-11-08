package com.jungeeks.controller;

import com.jungeeks.exception.InvalidRequestException;
import com.jungeeks.exception.TaskNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice()
public class TaskExceptionController {

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleMethodInvalidRequestException(InvalidRequestException invalidRequestException){
        log.warn("Bad request by {}", invalidRequestException.getMessage());
        return new ResponseEntity<>(invalidRequestException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TaskNotFoundException.class)
    public ResponseEntity<Object> handleMethodFamilyNotFoundException(TaskNotFoundException taskNotFoundException){
        log.warn("Bad request by {}", taskNotFoundException.getMessage());
        return new ResponseEntity<>(taskNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
