package com.jungeeks.service.dto;

import com.jungeeks.dto.FamilyIdDto;
import com.jungeeks.dto.UserInfoDto;

public interface UserInfoService {

    UserInfoDto getUserInfoByUserId(Long id);

    UserInfoDto getUserInfoByUserUId(String uId);

    FamilyIdDto getFamilyId();
}
