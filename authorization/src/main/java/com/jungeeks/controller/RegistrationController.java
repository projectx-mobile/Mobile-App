package com.jungeeks.controller;

import com.google.firebase.auth.FirebaseAuth;
import com.jungeeks.config.FirebaseConfig;
import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<String> registerUser(@AuthenticationPrincipal SecurityUserFirebase securityUserFirebase){
        //TODO:save user to db

        return ResponseEntity.status(HttpStatus.OK).body("registered");
    }
}
