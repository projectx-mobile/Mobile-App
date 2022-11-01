package com.jungeeks.service.business;

import com.jungeeks.entity.enums.USER_STATUS;

/**
 * The interface Edit user service.
 */
public interface EditUserService {
    /**
     * Change user name.
     *
     * @param userName the user name
     */
    void changeUserName(String userName);

    /**
     * Change user status.
     *
     * @param user_status the user status
     */
    void changeUserStatus(USER_STATUS user_status);

    void deleteFamilyMember(Long userId);
}
