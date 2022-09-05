package com.jungeeks.email.controllers;


import com.jungeeks.email.repo.EmailSender;
import com.jungeeks.email.repo.UserRepository;
import com.jungeeks.email.request.EmailRequest;
import com.jungeeks.email.services.ConfirmationTokenService;
import com.jungeeks.email.services.EmailValidator;
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
    @Autowired
    EmailValidator emailValidator;

    @PostMapping()
    public String verifyEmail(@RequestBody EmailRequest email) {
        boolean isValidEmail = emailValidator.test(email.getEmail());
        if (!isValidEmail) {
            throw new IllegalStateException("email not valid");
        }
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
