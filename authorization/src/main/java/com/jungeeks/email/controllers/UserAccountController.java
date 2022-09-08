package com.jungeeks.email.controllers;

import com.jungeeks.email.repo.ConfirmationTokenRepository;
import com.jungeeks.email.services.EmailService;
import com.jungeeks.email.repo.UserRepository;
import com.jungeeks.email.controllers.request.EmailRequest;
import com.jungeeks.email.services.imp.ConfirmationTokenServiceImp;
import com.jungeeks.email.entity.ConfirmationToken;
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
    EmailService emailSender;

    @Autowired
    ConfirmationTokenServiceImp confirmationTokenService;

    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;

    @PostMapping()
    public String verifyEmail(@RequestBody EmailRequest email) {
        String token = UUID.randomUUID().toString();

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        );

        confirmationTokenRepository.save(confirmationToken);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        String link = "http://localhost:8000/email/confirm?token=" + token;

        emailSender.send(email.getEmail(),
                emailSender.buildEmail(email.getEmail(), link));
        return token;
    }

    @GetMapping(path = "confirm")
    public String confirmToken(@RequestParam("token") String token) {
        return emailSender.confirmToken(token);
    }


}
