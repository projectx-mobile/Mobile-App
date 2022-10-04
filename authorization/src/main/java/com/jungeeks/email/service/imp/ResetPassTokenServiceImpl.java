package com.jungeeks.email.service.imp;

import com.jungeeks.email.entity.ResetPassToken;
import com.jungeeks.email.repo.ResetPassTokenRepository;
import com.jungeeks.email.service.ResetPassTokenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ResetPassTokenServiceImpl implements ResetPassTokenService {

    private ResetPassTokenRepository resetPassTokenRepository;

    @Override
    public void saveResetPassToken(String token, String email) {
        ResetPassToken resetPassToken = new ResetPassToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                email
        );
        resetPassTokenRepository.save(resetPassToken);

    }

    @Override
    public Optional<ResetPassToken> getToken(String token) {
        return resetPassTokenRepository.findByToken(token);
    }

    @Override
    public int setConfirmedAt(String token) {
        return resetPassTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }
    @Override
    public boolean isConfirmedAt(String email) {
        return resetPassTokenRepository.isConfirmedAt(email) == 0;
    }
}
