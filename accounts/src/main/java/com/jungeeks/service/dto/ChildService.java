package com.jungeeks.service.dto;

import com.jungeeks.dto.NotificationDto;
import com.jungeeks.entity.User;

import java.util.List;

public interface ChildService {
    List<NotificationDto> getDeadlineOfAllTask(User user);
}
