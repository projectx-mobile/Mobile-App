package com.jungeeks.registration.service;

import com.jungeeks.registration.request.RegistrationRequest;
import org.springframework.http.ResponseEntity;

public interface RegistrationService {

    ResponseEntity<String> register(RegistrationRequest request);

}
