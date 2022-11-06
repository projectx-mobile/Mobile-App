package com.jungeeks.controller;

import com.jungeeks.dto.SaveNewTaskDto;
import com.jungeeks.service.dto.ChildTaskService;
import com.jungeeks.service.dto.impl.ChildTaskServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/task")
@RestController
@Slf4j
public class TaskController {

    private ChildTaskService taskService;

    @Autowired
    public TaskController(ChildTaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/child/new-task")
    public ResponseEntity saveNewTask(@RequestBody SaveNewTaskDto saveNewTaskDto) {
        taskService.saveTask(saveNewTaskDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
