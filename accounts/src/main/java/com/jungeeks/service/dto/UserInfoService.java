package com.jungeeks.service.dto;

import com.jungeeks.dto.UserInfoDto;

public interface UserInfoService {
    UserInfoDto getUserInfoByUserId(Long id);
}
