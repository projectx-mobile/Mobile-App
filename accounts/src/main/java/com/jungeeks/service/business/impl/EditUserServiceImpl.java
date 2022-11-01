package com.jungeeks.service.business.impl;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.NotEnoughRightsException;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.service.business.EditUserService;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EditUserServiceImpl implements EditUserService {

    private UserService userService;

    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier("accounts_userServiceImpl")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void changeUserName(String userName) {
        String uid = getUid();
        userService.changeUserName(uid, userName);
    }

    @Override
    public void changeUserStatus(USER_STATUS user_status) {
        String uid = getUid();
        userService.changeUserStatus(uid, user_status);
    }

    @Override
    public void deleteFamilyMember(Long userId) {
        String uid = authorizationService.getUser().getUid();
        User user = userService.getUserByUid(uid);
        if (user.getUser_role() == USER_ROLE.PARENT) {
            userService.deleteFamilyMember(userId);
        } else {
            throw new NotEnoughRightsException("Insufficient rights to execute the request");
        }
    }

    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
