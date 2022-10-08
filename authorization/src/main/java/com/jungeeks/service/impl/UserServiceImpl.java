package com.jungeeks.service.impl;

import com.jungeeks.entity.User;
import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void createUser(SecurityUserFirebase securityUserFirebase) {
        User user = User.builder()
                .build();
        save(user);

    }
}
