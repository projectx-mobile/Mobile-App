package com.jungeeks.controller;

import com.jungeeks.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Slf4j
@ControllerAdvice()
public class AccountsExceptionController {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleMethodFamilyNotFoundException(BusinessException businessException){
        if (businessException.getError_code() == null && businessException.getHttpStatus() == HttpStatus.OK) {
            log.warn(BAD_REQUEST + " by {}", businessException.getMessage());

            return new ResponseEntity<>(businessException.getMessage(), BAD_REQUEST);
        }

        if (businessException.getHttpStatus() == HttpStatus.OK) {
            log.warn(BAD_REQUEST + " by {}", businessException.getMessage());

            return new ResponseEntity<>(businessException.getError_code().getMessage(), BAD_REQUEST);
        }

        if(businessException.getError_code() == null) {
            log.warn(businessException.getHttpStatus() + " by {}", businessException.getMessage());

            return new ResponseEntity<>(businessException.getMessage(), businessException.getHttpStatus());
        }

        log.warn(businessException.getHttpStatus() + " by {}", businessException.getMessage());

        return new ResponseEntity<>(businessException.getError_code().getMessage(), businessException.getHttpStatus());
    }
}
