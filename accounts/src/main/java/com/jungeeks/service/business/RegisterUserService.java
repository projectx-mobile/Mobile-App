package com.jungeeks.service.business;

import com.jungeeks.entity.enums.USER_ROLE;

public interface RegisterUserService {

    void registerByInvite(String username, String familyId, USER_ROLE user_role);

    void registerParentUser(String username);
}
