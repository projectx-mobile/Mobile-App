package com.jungeeks.service.business;

import com.jungeeks.entity.enums.USER_ROLE;

public interface RegisterUserService {

    boolean registerByInvite(String username, String familyId, USER_ROLE user_role);

    boolean registerParentUser(String username);
}
