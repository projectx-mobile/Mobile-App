package com.jungeeks.registration.service.imp;

import com.jungeeks.email.entity.User;
import com.jungeeks.entitiy.User;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.email.service.ConfirmationTokenService;
import com.jungeeks.registration.request.RegistrationRequest;
import com.jungeeks.registration.service.RegistrationService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RegistrationServiceImp implements RegistrationService {

    private final UserRepository userRepository;
    private final ConfirmationTokenService confirmationTokenService;

    public RegistrationServiceImp(UserRepository userRepository, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.confirmationTokenService = confirmationTokenService;
    }

    @Override
    public ResponseEntity<String> register(RegistrationRequest request) {
        if (confirmationTokenService.isConfirmedAt(request.getEmail())) {
            log.info("User is'n confirmed");
            return ResponseEntity.badRequest().body("User is'n confirmed");
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            log.info("User already exist");
            return ResponseEntity.badRequest().body("User already exist");
        }
        userRepository.save(
                User.builder()
                        .name(request.getName())
                        .email(request.getEmail())

                        .build()
                /*new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        )*/);

        return ResponseEntity.ok().body("User successfully registered");
    }
}
