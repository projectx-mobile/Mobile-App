package com.jungeeks.email.services;

import com.jungeeks.email.token.ConfirmationToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    public void saveConfirmationToken(ConfirmationToken token){
//      TODO: We need to save a confirmation token to DB
    }
}
