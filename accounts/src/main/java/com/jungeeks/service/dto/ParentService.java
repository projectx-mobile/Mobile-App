package com.jungeeks.service.dto;

import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entity.User;

public interface ParentService {

    ParentHomeDto getParentHomeDate(User user);
}
