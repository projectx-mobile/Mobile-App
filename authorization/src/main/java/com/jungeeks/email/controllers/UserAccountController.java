package com.jungeeks.email.controllers;


import com.jungeeks.email.entity.User;
import com.jungeeks.email.repo.EmailSender;
import com.jungeeks.email.repo.UserRepository;
import com.jungeeks.email.request.EmailRequest;
import com.jungeeks.email.services.ConfirmationTokenService;
import com.jungeeks.email.token.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("email")
public class UserAccountController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailSender emailSender;

    @Autowired
    ConfirmationTokenService confirmationTokenService;

    @PostMapping()
    public String verifyEmail(@RequestBody EmailRequest email) {
        //TODO: add check for mail exists
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        );

        String link = "http://localhost:8000/email/confirm?token=" + token;
        emailSender.send(email.getEmail(),
                emailSender.buildEmail(email.getEmail(), link));
        return token;
    }

    @GetMapping(path = "confirm")
    public String confirmToken(@RequestParam("token") String token) {
//        emailSender.confirmToken(token);
        return "it works";
    }


}
