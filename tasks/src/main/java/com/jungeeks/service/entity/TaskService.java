package com.jungeeks.service.entity;

import com.jungeeks.entity.Task;

public interface TaskService {

    Task findByTitle(String title);

    Task save(Task task);
}
