package com.jungeeks.email.repo;

public interface EmailSender {
    void send(String to, String email);

    String buildEmail(String name, String link);

    String confirmToken(String token);
}
