package com.jungeeks.service.impl;

import com.jungeeks.entity.User;
import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void checkUser(SecurityUserFirebase user) {
        User userDB = userRepository.findByFirebaseId(user.getUid());

        if (Objects.isNull(userDB)){
            userDB=User.builder()
                    .firebaseId(user.getUid())
                    .email(user.getEmail())
                    .build();
            userRepository.save(userDB);
        }else {
            if (!userDB.getEmail().equals(user.getEmail())){
                userDB.setEmail(user.getEmail());
            }
        }
    }
}
