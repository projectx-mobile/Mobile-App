package com.jungeeks.service.entity;

import com.jungeeks.entity.Task;

public interface TaskService {

    Task findByTemplate(String template);

    Task save(Task task);
}
