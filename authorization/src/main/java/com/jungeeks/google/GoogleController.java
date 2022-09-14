package com.jungeeks.google;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@CrossOrigin
public class GoogleController {
    @GetMapping("/google/login")
    public Principal user(Principal principal) {
        return principal;
    }

}
