package com.jungeeks.controller;

import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration/client")
public class ClientRegistrationController {

    @Autowired
    @Qualifier("auth_userService")
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<HttpStatus> registerClient(@RequestParam(name = "registration_token") String registration_token) {
        userService.updateAppRegistrationToken(registration_token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
