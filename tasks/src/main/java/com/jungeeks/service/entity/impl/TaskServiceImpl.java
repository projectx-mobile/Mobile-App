package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.Task;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.repository.TaskRepository;
import com.jungeeks.service.entity.TaskService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task findByTitle(String title) {
        return taskRepository.findByTitle(title).orElseThrow(
                () -> new BusinessException(String.format("Task with template %s not found", title), NOT_FOUND));
    }

    @Override
    public Task save(Task task) {
        return taskRepository.save(task);
    }
}
