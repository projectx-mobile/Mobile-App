package com.jungeeks.service.business.impl;

import com.jungeeks.RandomString;
import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.exception.enums.ERROR_CODE;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.business.RegisterUserService;
import com.jungeeks.service.entity.FamilyService;
import com.jungeeks.service.entity.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("accounts-registerUserServiceImpl")
public class RegisterUserServiceImpl implements RegisterUserService {

    private final AuthorizationService authorizationService;
    private final FamilyService familyService;
    private final UserService userService;

    public static final String DEFAULT_PHOTO_PATH = "default_account_photo.jpeg";

    @Autowired
    public RegisterUserServiceImpl(AuthorizationService authorizationService, FamilyService familyService,
                                   @Qualifier("accounts-userServiceImpl") UserService userService) {
        this.authorizationService = authorizationService;
        this.familyService = familyService;
        this.userService = userService;
    }

    @Transactional
    @Modifying
    @Override
    public boolean registerByInvite(String username, String familyId, USER_ROLE user_role) {
        String uid = getUser();
        Family family = familyService.getFamilyById(familyId);

        User user = userService.getUserByUid(uid);
        if (user.getFamily() != null) {
            throw new BusinessException(String.format("User with uid %s is already exist", uid), ERROR_CODE.USER_IS_ALREADY_EXIST);
        }
        user.setPoints(0L);
        user.setName(username);
        user.setFamily(family);
        user.setUser_role(user_role);
        user.setUser_status(USER_STATUS.ACTIVE);
        user.setPhoto(List.of(Photo.builder()
                .path(DEFAULT_PHOTO_PATH)
                .build()));
        switch (user_role) {
            case PARENT -> user.setParentNotifications(ParentNotification.builder()
                    .allOff(false)
                    .newTaskStatus(true)
                    .newRequest(true)
                    .newReward(true)
                    .build());
            case CHILD -> user.setChildNotifications(ChildNotification.builder()
                    .allOff(false)
                    .newTask(true)
                    .confirmReward(true)
                    .confirmTask(true)
                    .penaltyAndBonus(true)
                    .taskReminder(true)
                    .build());
        }
        return true;
    }

    @Transactional
    @Modifying
    @Override
    public boolean registerParentUser(String username) {
        String uid = getUser();
        RandomString randomString = new RandomString(12);
        Family build = Family.builder()
                .id(randomString.nextString())
                .build();
        Family familyDb = familyService.save(build);
        User user = userService.getUserByUid(uid);
        if (user.getFamily() != null) {
            throw new BusinessException(String.format("User with uid %s is already exist", uid), ERROR_CODE.USER_IS_ALREADY_EXIST);
        }
        user.setPoints(0L);
        user.setName(username);
        user.setFamily(familyDb);
        user.setUser_role(USER_ROLE.ADMIN);
        user.setUser_status(USER_STATUS.ACTIVE);
        user.setPhoto(List.of(Photo.builder()
                .path(DEFAULT_PHOTO_PATH)
                .build()));
        user.setParentNotifications(ParentNotification.builder()
                .allOff(false)
                .newTaskStatus(true)
                .newRequest(true)
                .newReward(true)
                .build());
        return true;
    }


    private String getUser() {
        return authorizationService.getUser().getUid();
    }
}
