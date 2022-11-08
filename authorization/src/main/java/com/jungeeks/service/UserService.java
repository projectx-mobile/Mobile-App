package com.jungeeks.service;

import com.jungeeks.security.entity.SecurityUserFirebase;

public interface UserService {

    void checkUser(SecurityUserFirebase user);
    boolean updateAppRegistrationToken(String registrationToken);
    boolean checkUserByContainsRegistrationToken();
    boolean checkUserStatus(SecurityUserFirebase userFirebase);
}
