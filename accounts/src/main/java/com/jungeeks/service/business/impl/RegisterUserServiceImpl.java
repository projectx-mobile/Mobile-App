package com.jungeeks.service.business.impl;

import com.jungeeks.RandomString;
import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import com.jungeeks.entity.*;
import com.jungeeks.entity.enums.NOTIFICATION_PERIOD;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.exception.FamilyNotFoundException;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.AccountsFamilyRepository;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.business.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * The type Register user service.
 */
@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private AccountsUserRepository userRepository;
    private AuthorizationService authorizationService;
    private AccountsFamilyRepository familyRepository;

    public static final String DEFAULT_PHOTO_PATH = "default_account_photo.jpeg";

    @Autowired
    @Qualifier("accounts_userRepository")
    public void setUserRepository(AccountsUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Autowired
    public void setFamilyRepository(AccountsFamilyRepository familyRepository) {
        this.familyRepository = familyRepository;
    }

    @Transactional
    @Modifying
    @Override
    public void registerByInvite(String username, String familyId, USER_ROLE user_role) {
        SecurityUserFirebase authUser = authorizationService.getUser();
        String uid = authUser.getUid();
        Family family = familyRepository.findById(familyId)
                .orElseThrow(() -> new FamilyNotFoundException(String.format("Family with id %s not found", familyId)));
        User user = userRepository.findByFirebaseId(uid)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uid)));

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
    }

    @Transactional
    @Modifying
    @Override
    public void registerParentUser(String username) {
        SecurityUserFirebase authUser = authorizationService.getUser();
        String uid = authUser.getUid();
        RandomString randomString = new RandomString(12);
        Family build = Family.builder()
                .id(randomString.nextString())
                .build();
        Family familyDb = familyRepository.save(build);
        User user = userRepository.findByFirebaseId(uid)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uid)));
        user.setPoints(0L);
        user.setName(username);
        user.setFamily(familyDb);
        user.setUser_role(USER_ROLE.PARENT);
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
    }
}
