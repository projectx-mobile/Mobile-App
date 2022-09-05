package com.jungeeks.google.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@CrossOrigin()
public class GoogleController {

    @GetMapping("google")
    public Principal user(Principal principal) {
        return principal;
    }

}
