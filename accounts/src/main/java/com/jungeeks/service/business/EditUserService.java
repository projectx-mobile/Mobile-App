package com.jungeeks.service.business;

import com.jungeeks.entity.enums.USER_STATUS;

public interface EditUserService {

    void changeUserName(String userName);

    void changeUserStatus(USER_STATUS user_status);

    void deleteFamilyMember(Long userId);
}
