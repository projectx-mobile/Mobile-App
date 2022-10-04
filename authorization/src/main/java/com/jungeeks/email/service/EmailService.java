package com.jungeeks.email.service;

import com.jungeeks.email.controller.request.ResetRequest;
import org.springframework.http.ResponseEntity;

public interface EmailService {
    void send(String to, String email, String emailType);

    String buildEmail(String name, String link);

    ResponseEntity<String> confirmToken(String token);
    String buildResetPassEmail(String name, String link);
    ResponseEntity<String> resetPassToken(String token);
}
