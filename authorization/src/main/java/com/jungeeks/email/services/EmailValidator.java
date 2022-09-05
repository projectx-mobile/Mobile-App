package com.jungeeks.email.services;

import org.springframework.web.bind.annotation.RestController;

import java.util.function.Predicate;

public class EmailValidator implements Predicate<String> {
    /*
     We should change EmailValidator on EmailRequest,
     which should have @Email annotation
  */
    @Override
    public boolean test(String s) {
        // TODO: Regex to validate email
        return true;
    }
}
