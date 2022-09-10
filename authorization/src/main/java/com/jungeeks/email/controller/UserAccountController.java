package com.jungeeks.email.controller;

import com.jungeeks.email.repo.ConfirmationTokenRepository;
import com.jungeeks.email.service.EmailService;
import com.jungeeks.email.repo.UserRepository;
import com.jungeeks.email.controller.request.EmailRequest;
import com.jungeeks.email.service.imp.ConfirmationTokenServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("email")
public class UserAccountController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailSender;

    @Autowired
    ConfirmationTokenServiceImp confirmationTokenService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @PostMapping()
    public ResponseEntity verifyEmail(@Valid @RequestBody EmailRequest email) {
        String token = UUID.randomUUID().toString();

        confirmationTokenService.saveConfirmationToken(token);

        String link = "http://localhost:8000/email/confirm?token=" + token;

        emailSender.send(email.getEmail(),
                emailSender.buildEmail(email.getEmail(), link));

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping(path = "confirm")
    public ResponseEntity confirmToken(@RequestParam("token") String token) {
        return emailSender.confirmToken(token);
    }


}
