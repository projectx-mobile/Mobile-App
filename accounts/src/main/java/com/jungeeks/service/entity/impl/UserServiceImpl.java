package com.jungeeks.service.entity.impl;

import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.ChildNotification;
import com.jungeeks.entity.ParentNotification;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.entity.enums.USER_STATUS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service("accounts_userServiceImpl")
public class UserServiceImpl implements UserService {

    private AccountsUserRepository accountsUserRepository;

    @Autowired
    @Qualifier("accounts_userRepository")
    public void setUserRepository(AccountsUserRepository accountsUserRepository) {
        this.accountsUserRepository = accountsUserRepository;
    }

    @Override
    public User getUserById(Long userId) {
        return accountsUserRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", userId)));
    }

    @Override
    public List<User> getAllByFamilyId(String familyId) {
        log.debug("Request getAllByFamilyId by familyId {}", familyId);
        return accountsUserRepository.findAllByFamilyId(familyId).orElseThrow(
                () -> new UserNotFoundException(String.format("Family with id %s not found", familyId)));
    }

    @Override
    public List<User> getAllByFamilyIdAndUserRole(String familyId, USER_ROLE user_role) {
        log.debug("Request getAllByFamilyIdAndUserRole by familyId {} and userRole {}", familyId, user_role);
        return accountsUserRepository.findAllByFamilyIdAndUser_role(familyId, user_role).orElseThrow(
                () -> new UserNotFoundException(String.format("User with familyId %s and role %s not found",
                        familyId, user_role))
        );
    }

    @Override
    public User getUserByUid(String uid) {
        log.debug("Request getUserByFirebaseId by uid {}", uid);
        return accountsUserRepository.findByFirebaseId(uid).orElseThrow(
                () -> new UserNotFoundException(String.format("User with uid %s not found", uid)));
    }

    @Transactional
    @Modifying
    @Override
    public boolean changeUserStatus(String uId, USER_STATUS newUserStatus) {
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
        return true;
    }

    @Transactional
    @Modifying
    @Override
    public boolean changeUserName(String uId, String newName) {
        User user = accountsUserRepository.findByFirebaseId(uId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with uid %s not found", uId)));
        user.setName(newName);
        log.debug(String.format("Changed username from uId:%s",uId));
        return true;
    }

    @Transactional
    @Modifying
    @Override
    public boolean deleteFamilyMember(Long userId) {
        User user = accountsUserRepository.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", userId)));
        user.setUser_status(USER_STATUS.REMOVED);
        log.debug(String.format("Delete family member by id %s",userId));
        return true;
    }
}
