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

    /**
     * Change user name.
     *
     * @param userName the user name
     */
    @Override
    public void changeUserName(String userName) {
        String uid = getUid();
        userService.changeUserName(uid, userName);
    }

    /**
     * Change user status.
     *
     * @param user_status the user status
     */
    @Override
    public void changeUserStatus(USER_STATUS user_status) {
        String uid = getUid();
        userService.changeUserStatus(uid, user_status);
    }

    /**
     * Delete family member.
     *
     * @param userId the user id
     */
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
