package com.jungeeks.service.busines.impl;

import com.jungeeks.RandomString;
import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import com.jungeeks.dto.enums.USER_ROLE_DTO;
import com.jungeeks.entity.Family;
import com.jungeeks.entity.ParentNotification;
import com.jungeeks.entity.Photo;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.AccountsFamilyRepository;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.busines.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    private AccountsUserRepository userRepository;
    private AuthorizationService authorizationService;
    private AccountsFamilyRepository familyRepository;
    private PhotoStorageService photoStorageService;
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
