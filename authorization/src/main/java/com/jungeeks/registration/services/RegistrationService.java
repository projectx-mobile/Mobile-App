package com.jungeeks.registration.services;

import com.jungeeks.email.entity.User;
import com.jungeeks.registration.requests.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
//@AllArgsConstructor
public class RegistrationService {

    @Autowired
    private AppUserService appUserService;

    public String register(RegistrationRequest request) {
        return appUserService.signUpUser(
                new User(
                        request.getName(),
                        request.getEmail(),
                        request.getPassword()
                ));
    }
}
