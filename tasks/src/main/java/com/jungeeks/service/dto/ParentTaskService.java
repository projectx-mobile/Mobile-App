package com.jungeeks.service.dto;

import com.jungeeks.dto.ConfirmTaskDto;
import com.jungeeks.dto.ParentNewTaskDto;

public interface ParentTaskService {

    boolean saveTask(ParentNewTaskDto parentNewTaskDto);

    boolean confirmTask(ConfirmTaskDto confirmTaskDto);

    boolean rejectTask(Long taskId);
}
