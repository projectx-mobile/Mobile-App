package com.jungeeks.email.service;

import org.springframework.http.ResponseEntity;

public interface EmailService {
    void send(String to, String email);

    String buildEmail(String name, String link);

    ResponseEntity<String> confirmToken(String token);
}
