package com.jungeeks.controller;

import com.jungeeks.auth.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @PostMapping
    public ResponseEntity<String> registerUser(@AuthenticationPrincipal User user){
        //TODO:save user to db
        return ResponseEntity.status(HttpStatus.OK).body("registered");
    }
}
