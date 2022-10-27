package com.jungeeks.accounts.service.dto;

import com.jungeeks.accounts.dto.UserInfoDto;

public interface UserInfoService {
    UserInfoDto getUserInfoByUserId(Long id);
    UserInfoDto getUserInfoByUserUId(String uId);
}
