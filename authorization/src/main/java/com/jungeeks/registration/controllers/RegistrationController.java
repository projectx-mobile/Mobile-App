package com.jungeeks.registration.controllers;

import com.jungeeks.email.entity.User;
import com.jungeeks.email.repo.UserRepository;
import com.jungeeks.registration.requests.RegistrationRequest;
import com.jungeeks.registration.services.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/registration")
//@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;
    private final UserRepository userRepository;

    public RegistrationController(RegistrationService registrationService, UserRepository userRepository) {
        this.registrationService = registrationService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) {
        if (!userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email is not exist");
        }
        if (userRepository.findByEmail(request.getEmail()).getName() != null) {
            return ResponseEntity.badRequest().body("User already exist");
        }

        User newUser = new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        userRepository.save(newUser);

        return ResponseEntity.ok().body("User successfully registered");
    }
}
