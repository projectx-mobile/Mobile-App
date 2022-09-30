package com.jungeeks.email.service.imp;

import com.jungeeks.email.repo.ConfirmationTokenRepository;
import com.jungeeks.email.entity.ConfirmationToken;
import com.jungeeks.email.service.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenServiceImp implements ConfirmationTokenService {

    private ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public void saveConfirmationToken(String token, String email) {
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                email
        );
        confirmationTokenRepository.save(confirmationToken);

    }

    @Override
    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    @Override
    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
    @Override
    public boolean isConfirmedAt(String email) {
        return confirmationTokenRepository.isConfirmedAt(email) == 0;
    }
}
