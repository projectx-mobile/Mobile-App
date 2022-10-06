package com.jungeeks.controller;

import com.jungeeks.email.controller.request.ResetRequest;
import com.jungeeks.email.service.EmailService;
import com.jungeeks.email.controller.request.EmailRequest;
import com.jungeeks.email.service.imp.ConfirmationTokenServiceImp;
import com.jungeeks.email.service.imp.ResetPassTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("email/")
public class UserAccountController {

    @Autowired
    private EmailService emailSender;

    @Autowired
    private ConfirmationTokenServiceImp confirmationTokenService;
    @Autowired
    private ResetPassTokenServiceImpl resetPassTokenService;

    @Value("${confirm.link}")
    private String confirmTokenLink;

    @Value("${reset.link}")
    private String resetTokenLink;

    @PostMapping("verify")
    public ResponseEntity<String> verifyEmail(@Valid @RequestBody EmailRequest email) {
        String token = UUID.randomUUID().toString();
        confirmationTokenService.saveConfirmationToken(token, email.getEmail());
        String confirmLink = confirmTokenLink + token;
        emailSender.send(email.getEmail(), confirmLink, "Confirm your email");

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token) {
        return emailSender.confirmToken(token);
    }

    @GetMapping(path = "reset")
    public ResponseEntity<String> resetPassToken(@RequestParam("token") String token) {
        return emailSender.resetPassToken(token);
    }
    @PostMapping("forgot")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody EmailRequest email) {
        String token = UUID.randomUUID().toString();
        resetPassTokenService.saveResetPassToken(token, email.getEmail());
        String resetLink = resetTokenLink + token;
        emailSender.send(email.getEmail(), resetLink, "Reset your password");

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

}
