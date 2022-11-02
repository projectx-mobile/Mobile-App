package com.jungeeks.controller;

import com.jungeeks.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice()
public class AccountsExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleMethodUserNotFoundException(UserNotFoundException userNotFoundException){
        log.warn("Bad request by {}", userNotFoundException.getMessage());
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PathNotFoundException.class)
    public ResponseEntity<Object> handleMethodPathNotFoundException(PathNotFoundException pathNotFoundException){
        log.warn("Bad request by {}", pathNotFoundException.getMessage());
        return new ResponseEntity<>(pathNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleMethodInvalidRequestException(InvalidRequestException invalidRequestException){
        log.warn("Bad request by {}", invalidRequestException.getMessage());
        return new ResponseEntity<>(invalidRequestException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FamilyNotFoundException.class)
    public ResponseEntity<Object> handleMethodFamilyNotFoundException(FamilyNotFoundException familyNotFoundException){
        log.warn("Bad request by {}", familyNotFoundException.getMessage());
        return new ResponseEntity<>(familyNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotEnoughRightsException.class)
    public ResponseEntity<Object> handleMethodNotEnoughRightsException(NotEnoughRightsException notEnoughRightsException){
       log.warn("Bad request by {}", notEnoughRightsException.getMessage());
       return new ResponseEntity<>(notEnoughRightsException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserIsAlreadyExistException.class)
    public ResponseEntity<Object> handleMethodUserIsAlreadyExistException(UserIsAlreadyExistException userIsAlreadyExistException){
        log.warn("Bad request by {}", userIsAlreadyExistException.getMessage());
        return new ResponseEntity<>("User is already exist", HttpStatus.FORBIDDEN);
    }
}
