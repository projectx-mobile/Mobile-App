package com.jungeeks.service.dto;

import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.dto.GetTaskDto;

import java.time.LocalDate;
import java.util.List;

public interface ChildTaskService {

    boolean saveTask(ChildNewTaskDto childNewTaskDto);

    List<GetTaskDto> getTasksByDate(LocalDate date);
}
