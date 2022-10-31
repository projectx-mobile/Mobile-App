package com.jungeeks.service.busines;

import com.jungeeks.dto.enums.USER_ROLE_DTO;
import com.jungeeks.entity.enums.USER_ROLE;

/**
 * The interface Register user service.
 */
public interface RegisterUserService {
    /**
     * Register by invite.
     *
     * @param username  the username
     * @param familyId  the family id
     * @param user_role the user role
     */
    void registerByInvite(String username, String familyId, USER_ROLE user_role);

    /**
     * Register parent user.
     *
     * @param username the username
     */
    void registerParentUser(String username);
}
