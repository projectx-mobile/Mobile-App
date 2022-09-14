package com.jungeeks.registration.service.imp;

import com.jungeeks.email.entity.User;
import com.jungeeks.email.repo.UserRepository;
import com.jungeeks.registration.request.RegistrationRequest;
import com.jungeeks.registration.service.RegistrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistrationServiceImp implements RegistrationService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(RegistrationServiceImp.class);
    private final UserRepository userRepository;

    public RegistrationServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<String> register(RegistrationRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            LOGGER.info("User already exist");
            return ResponseEntity.badRequest().body("User already exist");
        }
        userRepository.save(new User(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        ));

        return ResponseEntity.ok().body("User successfully registered");
    }
}
