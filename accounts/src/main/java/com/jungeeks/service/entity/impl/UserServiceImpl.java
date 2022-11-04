package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.ChildNotification;
import com.jungeeks.entity.ParentNotification;
import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.exception.enums.ERROR_CODE;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.entity.enums.USER_STATUS;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.jungeeks.exception.enums.ERROR_CODE.USER_NOT_FOUND;
import static com.jungeeks.exception.enums.ERROR_CODE.USER_WITH_FAMILY_ID_AND_ROLE;

@Slf4j
@Service("accounts-userServiceImpl")
public class UserServiceImpl implements UserService {

    private AccountsUserRepository accountsUserRepository;

    @Autowired
    public UserServiceImpl(AccountsUserRepository accountsUserRepository) {
        this.accountsUserRepository = accountsUserRepository;
    }

    @Override
    public User getUserById(Long userId) {
        log.debug("Request findUserById by id {}", userId);

        return accountsUserRepository.findUserById(userId)
                .orElseThrow(() -> new BusinessException(String.format("User with id %s not found", userId),
                        ERROR_CODE.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<User> getAllByFamilyId(String familyId) {
        log.debug("Request getAllByFamilyId by familyId {}", familyId);

        return accountsUserRepository.findAllByFamilyId(familyId).orElseThrow(
                () -> new BusinessException(String.format("Family with id %s not found", familyId),
                        ERROR_CODE.FAMILY_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public List<User> getAllByFamilyIdAndUserRole(String familyId, USER_ROLE user_role) {
        log.debug("Request getAllByFamilyIdAndUserRole by familyId {} and userRole {}", familyId, user_role);

        return accountsUserRepository.findAllByFamilyIdAndUser_role(familyId, user_role).orElseThrow(
                () -> new BusinessException(String.format("User with familyId %s and role %s not found",
                        familyId, user_role), USER_WITH_FAMILY_ID_AND_ROLE, HttpStatus.NOT_FOUND)
        );
    }

    @Override
    public User getUserByUid(String uid) {
        log.debug("Request getUserByFirebaseId by uid {}", uid);

        return accountsUserRepository.findByFirebaseId(uid).orElseThrow(
                () -> new BusinessException(String.format("User with uid %s not found", uid),
                        USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Transactional
    @Modifying
    @Override
    public boolean changeUserStatus(String uid, USER_STATUS newUserStatus) {
        User user = accountsUserRepository.findByFirebaseId(uid)
                .orElseThrow(() -> new BusinessException(String.format("User with uid %s not found", uid),
                        USER_NOT_FOUND, HttpStatus.NOT_FOUND));

        user.setUser_status(newUserStatus);

        if (newUserStatus.equals(USER_STATUS.REMOVED) || newUserStatus.equals(USER_STATUS.BANNED)){
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
        log.debug("Changed user_status from uId:{}",uid);

        return true;
    }

    @Transactional
    @Modifying
    @Override
    public boolean changeUserName(String uid, String newName) {
        User user = accountsUserRepository.findByFirebaseId(uid)
                .orElseThrow(() -> new BusinessException(String.format("User with uid %s not found", uid),
                        USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setName(newName);
        log.debug("Changed username from uId:{}",uid);

        return true;
    }

    @Transactional
    @Modifying
    @Override
    public boolean deleteFamilyMember(Long userId) {
        User user = accountsUserRepository.findUserById(userId)
                .orElseThrow(() -> new BusinessException(String.format("User with id %s not found", userId),
                USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        user.setUser_status(USER_STATUS.REMOVED);
        log.debug("Delete family member by id {}",userId);

        return true;
    }
}
