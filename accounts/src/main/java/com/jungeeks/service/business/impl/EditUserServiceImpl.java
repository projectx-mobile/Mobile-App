package com.jungeeks.service.business.impl;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.exception.enums.ERROR_CODE;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.service.business.EditUserService;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service("accounts-editUserServiceImpl")
public class EditUserServiceImpl implements EditUserService {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    @Autowired
    public EditUserServiceImpl(@Qualifier("accounts-userServiceImpl")UserService userService,
                               AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @Override
    public boolean changeUserName(String userName) {
        return userService.changeUserName(getUid(), userName);
    }

    @Override
    public boolean changeUserStatus(USER_STATUS user_status) {
        return userService.changeUserStatus(getUid(), user_status);
    }

    @Override
    public boolean deleteFamilyMember(Long userId) {
        User user = userService.getUserByUid(getUid());
        if (user.getUser_role() == USER_ROLE.PARENT) {
            return userService.deleteFamilyMember(userId);
        } else {
            throw new BusinessException("Insufficient rights to execute the request", ERROR_CODE.NOT_ENOUGH_RIGHTS);
        }
    }

    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
