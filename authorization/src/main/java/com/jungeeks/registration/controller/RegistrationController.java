package com.jungeeks.registration.controller;

import com.jungeeks.registration.request.RegistrationRequest;
import com.jungeeks.registration.service.imp.RegistrationServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RegistrationController {

    private final RegistrationServiceImp registrationService;

    public RegistrationController(RegistrationServiceImp registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/api/registration")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
        return registrationService.register(request);

    }
}
