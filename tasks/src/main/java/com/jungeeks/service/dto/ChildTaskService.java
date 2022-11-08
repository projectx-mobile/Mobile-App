package com.jungeeks.service.dto;

import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.dto.TaskDto;

import java.util.List;

public interface ChildTaskService {

    boolean saveTask(ChildNewTaskDto childNewTaskDto);
    List<TaskDto> getAllTasksByUserUid();
    List<TaskDto> getAllTasksByUserId(Long userid);
}
