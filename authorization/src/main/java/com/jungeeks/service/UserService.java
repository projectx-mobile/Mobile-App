package com.jungeeks.service;

import com.jungeeks.entity.User;
import com.jungeeks.entity.SecurityUserFirebase;

public interface UserService {
    void save(User user);
    void createUser(SecurityUserFirebase securityUserFirebase);
}
