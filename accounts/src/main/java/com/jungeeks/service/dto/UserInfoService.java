package com.jungeeks.service.dto;

import com.jungeeks.dto.UserInfoDto;

/**
 * The interface User info service.
 */
public interface UserInfoService {
    /**
     * Gets user info by user id.
     *
     * @param id the id
     * @return the user info by user id
     */
    UserInfoDto getUserInfoByUserId(Long id);

    /**
     * Gets user info by user u id.
     *
     * @param uId the u id
     * @return the user info by user u id
     */
    UserInfoDto getUserInfoByUserUId(String uId);
}
