package com.jungeeks.controller;

import com.jungeeks.dto.VerifyRequestDto;
import com.jungeeks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration/client")
@RequiredArgsConstructor
public class ClientRegistrationController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestParam(name = "registration_token") String registration_token) {
        userService.updateAppRegistrationToken(registration_token);
        return ResponseEntity.ok("OK");
    }
}
