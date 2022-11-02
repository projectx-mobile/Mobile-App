package com.jungeeks.service.business;

import com.jungeeks.entity.enums.USER_STATUS;

public interface EditUserService {

    boolean changeUserName(String userName);

    boolean changeUserStatus(USER_STATUS user_status);

    boolean deleteFamilyMember(Long userId);
}
