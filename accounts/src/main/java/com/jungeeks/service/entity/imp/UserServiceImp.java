package com.jungeeks.service.entity.imp;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.AccountsUserRepository;
import com.jungeeks.service.entity.UserService;
import com.sun.istack.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link UserService}
 *
 * @author TorusTredent on 10.28.2022
 */
@Service("accounts_userServiceImpl")
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final AccountsUserRepository accountsUserRepository;

    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    @Override
    public User getUserById(Long id) {
        log.debug("Request getUserById by id {}", id);
        return accountsUserRepository.findUserById(id).orElseThrow(
                () -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    /**
     * Gets all by family id.
     *
     * @param familyId the family id
     * @return the all by family id
     */
    @Override
    public List<User> getAllByFamilyId(@NonNull String familyId) {
        log.debug("Request getAllByFamilyId by familyId {}", familyId);
        return accountsUserRepository.findAllByFamilyId(familyId).orElseThrow(
                () -> new UserNotFoundException(String.format("Family with id %s not found", familyId)));
    }

    /**
     * Gets all by family id and user role.
     *
     * @param familyId  the family id
     * @param user_role the user role
     * @return the all by family id and user role
     */
    @Override
    public List<User> getAllByFamilyIdAndUserRole(@NotNull String familyId, @NotNull USER_ROLE user_role) {
        log.debug("Request getAllByFamilyIdAndUserRole by familyId {} and userRole {}", familyId, user_role);
        return accountsUserRepository.findAllByFamilyIdAndUser_role(familyId, user_role).orElseThrow(
                () -> new UserNotFoundException(String.format("User with familyId %s and role %s not found",
                                                                                        familyId, user_role))
        );
    }

    /**
     * Gets user by firebase id.
     *
     * @param uid the uid
     * @return the user by firebase id
     */
    @Override
    public User getUserByFirebaseId(String uid) {
        log.debug("Request getUserByFirebaseId by uid {}", uid);
        return accountsUserRepository.findByFirebaseId(uid).orElseThrow(
                () -> new UserNotFoundException(String.format("User with uid %s not found", uid)));
    }
}
