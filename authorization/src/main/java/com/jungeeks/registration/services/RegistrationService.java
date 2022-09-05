package com.jungeeks.registration.services;

import com.jungeeks.email.entity.User;
import com.jungeeks.registration.requests.RegistrationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegistrationService {

//    private final AppUserService appUserService;

    public String register(RegistrationRequest request) {
        /*
         * Before it, we should to add a parameter in signUpUserMethod
         * */
//        return appUserService.signUpUser(
//                new User(
//                        request.getName(),
//                        request.getEmail(),
//                        request.getPassword()
//                )
//        );
        return "works";
    }
}
