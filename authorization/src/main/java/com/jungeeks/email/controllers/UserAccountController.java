package com.jungeeks.email.controllers;


import com.jungeeks.email.entity.User;
import com.jungeeks.email.repo.UserRepository;
import com.jungeeks.email.token.ConfirmationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/email")
public class UserAccountController {

    @Autowired
    UserRepository userRepository;

    @GetMapping
    public String verifyEmail(String email) {
//        User checkUserEmail = userRepository.findByEmail(email);
//        if (checkUserEmail != null) {
//            return "This email has another user";
//        } else {
////            checkUserEmail.setEmail(email);
//            userRepository.save(checkUserEmail);
//
//        }
        //TODO: add check for mail exists
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        );

        //TODO: SEND  EMAIL
        return token;
    }
}
