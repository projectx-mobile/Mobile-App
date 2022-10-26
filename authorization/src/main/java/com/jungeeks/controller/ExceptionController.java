package com.jungeeks.controller;

import com.jungeeks.exception.RegistrationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ExceptionController {

    @ExceptionHandler(RegistrationFailedException.class)
    public ResponseEntity<Object> handleMethodRegistrationFailedException(RegistrationFailedException registrationFailedException){
        log.warn(String.format("Bad request by %s", registrationFailedException.getMessage()));
        return new ResponseEntity<>(registrationFailedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MailException.class,MailSendException.class})
    public ResponseEntity<Object> handleMethodMailSendException(MailSendException mailSendException){
        String message= Arrays.stream(mailSendException.getMessageExceptions())
                .map(Throwable::getMessage)
                .collect(Collectors.joining());
        log.warn(String.format("Bad request by %s", message));
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
