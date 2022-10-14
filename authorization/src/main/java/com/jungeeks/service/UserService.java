package com.jungeeks.service;

import com.jungeeks.entity.User;
import com.jungeeks.entity.SecurityUserFirebase;

public interface UserService {

    void checkUser(SecurityUserFirebase user);
    boolean updateAppRegistrationToken(String registrationToken);
    boolean checkUserByContainsRegistrationToken();
}
