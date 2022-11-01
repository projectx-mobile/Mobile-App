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

/**
 * The type User service.
 */
@Slf4j
@Service("accountsUserService")
public class UserServiceImpl implements UserService {

    private AccountsUserRepository accountsUserRepository;
    private AuthorizationService authorizationService;

    /**
     * Sets user repository.
     *
     * @param accountsUserRepository the accounts user repository
     */
    @Autowired
    @Qualifier("accounts_userRepository")
    public void setUserRepository(AccountsUserRepository accountsUserRepository) {
        this.accountsUserRepository = accountsUserRepository;
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
     * Gets user by id.
     *
     * @param userId the user id
     * @return the user by id
     */
    @Override
    public User getUserById(Long userId) {
        return accountsUserRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", userId)));
    }

    /**
     * Gets user by uid.
     *
     * @param uId the u id
     * @return the user by uid
     */
    @Override
    public User getUserByUid(String uId) {
        return accountsUserRepository.findByFirebaseId(uId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uId)));
    }

    /**
     * Gets all by family id.
     *
     * @param familyId the family id
     * @return the all by family id
     */
    @Override
    public List<User> getAllByFamilyId(@NonNull String familyId) {
        return accountsUserRepository.findAllByFamilyId(familyId)
                .orElseThrow(() -> new UserNotFoundException(String.format("Family id %s not found", familyId)));
    }

    /**
     * Change user status.
     *
     * @param uId           the u id
     * @param newUserStatus the new user status
     */
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

    /**
     * Change user name.
     *
     * @param uId     the u id
     * @param newName the new name
     */
    @Transactional
    @Modifying
    @Override
    public void changeUserName(String uId, String newName) {
        User user = accountsUserRepository.findByFirebaseId(uId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uId)));
        user.setName(newName);
        log.debug(String.format("Changed username from uId:%s",uId));
    }

    /**
     * Delete family member.
     *
     * @param userId the user id
     */
    @Transactional
    @Modifying
    @Override
    public void deleteFamilyMember(Long userId) {
        User user = accountsUserRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", userId)));
        user.setFamily(null);
        log.debug(String.format("Delete family member by id %s",userId));
    }
}
