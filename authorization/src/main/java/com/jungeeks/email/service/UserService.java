package com.jungeeks.email.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResponseEntity<String> resetPassword(String email, String password, String confirmPassword);
}
