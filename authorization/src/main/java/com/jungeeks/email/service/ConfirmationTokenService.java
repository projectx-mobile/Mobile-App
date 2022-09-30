package com.jungeeks.email.service;

import com.jungeeks.email.entity.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {
    void saveConfirmationToken(String token, String email);

    Optional<ConfirmationToken> getToken(String token);

    int setConfirmedAt(String token);
    boolean isConfirmedAt(String email);
}
