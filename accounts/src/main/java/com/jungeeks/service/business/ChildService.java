package com.jungeeks.service.business;

import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.Task;

import java.util.List;

public interface ChildService {
    List<NotificationDto> getDeadlineOfAllTask();

    List<TaskDto> getUserTaskById();
}
