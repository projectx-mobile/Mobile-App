package com.jungeeks.email.services;

public interface EmailService {
    void send(String to, String email);

    String buildEmail(String name, String link);

    String confirmToken(String token);
}
