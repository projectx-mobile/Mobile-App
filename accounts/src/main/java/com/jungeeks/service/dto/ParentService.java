package com.jungeeks.service.dto;

import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entity.User;

public interface ParentService {

    /**
     * Gets parent home date.
     *
     * @param user the user
     * @return the parent home date
     */
    ParentHomeDto getParentHomeDate(User user);
}
