package com.jungeeks.service.entity.impl;


import com.jungeeks.entity.ChildNotification;
import com.jungeeks.entity.ParentNotification;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.security.service.AuthorizationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service("accountsUserService")
public class UserServiceImpl implements UserService {

    private AccountsUserRepository accountsUserRepository;
    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier("accounts_userRepository")
    public void setUserRepository(AccountsUserRepository accountsUserRepository) {
        this.accountsUserRepository = accountsUserRepository;
    }

    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public User getUserById(Long userId) {
        return accountsUserRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", userId)));
    }

    @Override
    public User getUserByUid(String uId) {
        return accountsUserRepository.findByFirebaseId(uId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uId)));
    }

    @Override
    public List<User> getAllByFamilyId(@NonNull String familyId) {
        return accountsUserRepository.findAllByFamilyId(familyId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Family id %s not found", familyId)));
    }

    @Transactional
    @Modifying
    @Override
    public void changeUserStatus(String uId, USER_STATUS newUserStatus) {
        User user = accountsUserRepository.findByFirebaseId(uId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uId)));
        user.setUser_status(newUserStatus);
        if (newUserStatus.equals(USER_STATUS.REMOVED)||newUserStatus.equals(USER_STATUS.BANNED)){
            USER_ROLE user_role = user.getUser_role();
            switch (user_role){
                case PARENT -> {
                    ParentNotification parentNotifications = user.getParentNotifications();
                    parentNotifications.setAllOff(true);
                    parentNotifications.setNewRequest(false);
                    parentNotifications.setNewTaskStatus(false);
                    parentNotifications.setNewReward(false);
                }
                case CHILD -> {
                    ChildNotification childNotifications = user.getChildNotifications();
                    childNotifications.setAllOff(true);
                    childNotifications.setConfirmTask(false);
                    childNotifications.setConfirmReward(false);
                    childNotifications.setNewTask(false);
                    childNotifications.setTaskReminder(false);
                    childNotifications.setPenaltyAndBonus(false);
                }
            }
        }
        log.debug(String.format("Changed user_status from uId:%s",uId));
    }

    @Transactional
    @Modifying
    @Override
    public void changeUserName(String uId, String newName) {
        User user = accountsUserRepository.findByFirebaseId(uId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uId)));
        user.setName(newName);
        log.debug(String.format("Changed username from uId:%s",uId));
    }

}
