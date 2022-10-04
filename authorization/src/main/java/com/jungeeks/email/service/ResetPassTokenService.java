package com.jungeeks.email.service;

import com.jungeeks.email.entity.ResetPassToken;

import java.util.Optional;

public interface ResetPassTokenService {
    void saveResetPassToken(String token, String email);

    Optional<ResetPassToken> getToken(String token);

    int setConfirmedAt(String token);
    boolean isConfirmedAt(String email);


}
