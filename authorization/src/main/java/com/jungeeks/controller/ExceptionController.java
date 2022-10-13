package com.jungeeks.controller;

import com.jungeeks.exception.RegistrationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<Object> handleMethodRegistrationFailedException(RegistrationFailedException registrationFailedException){
        log.warn(String.format("Bad request by %s", registrationFailedException.getMessage()));
        return new ResponseEntity<>(registrationFailedException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
