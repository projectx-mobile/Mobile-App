package com.jungeeks.service.busines.impl;

import com.jungeeks.service.entity.UserService;
import com.jungeeks.service.busines.EditUserService;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * The type Edit user service.
 */
@Slf4j
@Service
public class EditUserServiceImpl implements EditUserService {

    private UserService userService;

    private AuthorizationService authorizationService;

    /**
     * Sets user service.
     *
     * @param userService the user service
     */
    @Autowired
    @Qualifier("accountsUserService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sets authorization service.
     *
     * @param authorizationService the authorization service
     */
    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }


    @Override
    public void changeUserName(String userName) {
        String uid = authorizationService.getUser().getUid();
        userService.changeUserName(uid, userName);
    }

    @Override
    public void changeUserStatus(USER_STATUS user_status) {
        String uid = authorizationService.getUser().getUid();
        userService.changeUserStatus(uid, user_status);
    }
}
