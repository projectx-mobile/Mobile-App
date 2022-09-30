package com.jungeeks.email.controller;

import com.jungeeks.email.service.EmailService;
import com.jungeeks.email.controller.request.EmailRequest;
import com.jungeeks.email.service.imp.ConfirmationTokenServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("email")
public class UserAccountController {

    @Autowired
    private EmailService emailSender;

    @Autowired
    private ConfirmationTokenServiceImp confirmationTokenService;

    @Value("${confirm.link}")
    private String link;

    @PostMapping()
    public ResponseEntity<String> verifyEmail(@Valid @RequestBody EmailRequest email) {
        String token = UUID.randomUUID().toString();
        confirmationTokenService.saveConfirmationToken(token, email.getEmail());
        String confirmLink = link + token;
        emailSender.send(email.getEmail(), confirmLink);

        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping(path = "confirm")
    public ResponseEntity<String> confirmToken(@RequestParam("token") String token) {
        return emailSender.confirmToken(token);
    }


}
