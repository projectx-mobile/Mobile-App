package com.jungeeks.email.service.imp;

import com.jungeeks.email.entity.User;
import com.jungeeks.email.repo.UserRepository;
import com.jungeeks.email.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public ResponseEntity<String> resetPassword(String email, String newPassword, String confirmPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalStateException("Email not found"));
        if (newPassword.equals(confirmPassword)) {
            user.setPassword(newPassword);
            userRepository.save(user);
        } else {
            throw new IllegalStateException("Password and Confirm Password must be the same");
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password has been changed");
    }
}
