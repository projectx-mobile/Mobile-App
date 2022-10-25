package com.jungeeks.service.impl;

import com.jungeeks.entity.ClientApp;
import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.entity.User;
import com.jungeeks.exception.RegistrationFailedException;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.SecurityService;
import com.jungeeks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private SecurityService securityService;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Transactional
    @Override
    public void checkUser(SecurityUserFirebase user) {
        User userDB = userRepository.findByFirebaseId(user.getUid()).orElse(null);

        if (Objects.isNull(userDB)) {
            userDB = User.builder()
                    .firebaseId(user.getUid())
                    .email(user.getEmail())
                    .build();
            userRepository.save(userDB);
            log.debug("User with Uid:" + userDB.getFirebaseId() + " added to db");
        } else {
            if (!userDB.getEmail().equals(user.getEmail())) {
                userDB.setEmail(user.getEmail());
                log.debug("User email updated");
            }
        }
    }

    @Transactional
    @Override
    public boolean updateAppRegistrationToken(String registrationToken) {
        User userDb = userRepository.findByFirebaseId(securityService.getUser().getUid()).orElseThrow(
                () -> new RegistrationFailedException("User not found"));

        Optional<ClientApp> first = userDb.getClientApps().stream()
                .filter(x -> x.getAppId().equals(registrationToken))
                .findFirst();

        if (first.isPresent()){
            ClientApp clientApp = first.orElse(null);
            clientApp.setUpdated(LocalDateTime.now());
        }else {
            userDb.getClientApps().add(ClientApp.builder()
                    .appId(registrationToken)
                    .updated(LocalDateTime.now())
                    .build());
        }
        log.debug("User app registration token updated");
        return true;
    }

    @Override
    public boolean checkUserByContainsRegistrationToken() {
        User userDb = userRepository.findByFirebaseId(securityService.getUser().getUid()).orElse(null);
        return Objects.nonNull(userDb.getClientApps());
    }
}
