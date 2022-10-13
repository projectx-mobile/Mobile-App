package com.jungeeks.service.impl;

import com.jungeeks.entity.User;
import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.repository.JpaUserRepository;
import com.jungeeks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    private JpaUserRepository jpaUserRepository;

    @Autowired
    public void setUserRepository(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User findByFirebaseId(String firebaseId) {
        return jpaUserRepository.findByFirebaseId(firebaseId).orElse(null);
    }

    @Transactional
    @Override
    public void checkUser(SecurityUserFirebase user) {
        User userDB = findByFirebaseId(user.getUid());

        if (userDB == null){
            userDB=User.builder()
                    .firebaseId(user.getUid())
                    .email(user.getEmail())
                    .build();
            jpaUserRepository.save(userDB);
        }else {
            if (!userDB.getEmail().equals(user.getEmail())){
                userDB.setEmail(user.getEmail());
            }
        }
    }
}
