package com.jungeeks.service.entity;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_STATUS;

import java.util.List;

/**
 * The interface User service.
 */
public interface UserService {
    /**
     * Gets user by id.
     *
     * @param id the id
     * @return the user by id
     */
    User getUserById(Long id);

    /**
     * Gets user by uid.
     *
     * @param uId the u id
     * @return the user by uid
     */
    User getUserByUid(String uId);

    /**
     * Gets all by family id.
     *
     * @param familyId the family id
     * @return the all by family id
     */
    List<User> getAllByFamilyId(String familyId);

    /**
     * Change user status.
     *
     * @param uId           the u id
     * @param newUserStatus the new user status
     */
    void changeUserStatus(String uId, USER_STATUS newUserStatus);

    /**
     * Change user name.
     *
     * @param uId     the u id
     * @param newName the new name
     */
    void changeUserName(String uId, String newName);

    /**
     * Delete family member.
     *
     * @param userId the user id
     */
    void deleteFamilyMember(Long userId);
}
