package com.jungeeks.controller;

import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.dto.ParentNewTaskDto;
import com.jungeeks.service.dto.ChildTaskService;
import com.jungeeks.service.dto.ParentTaskService;
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

    private final ChildTaskService childTaskService;
    private final ParentTaskService parentTaskService;

    @Autowired
    public TaskController(ChildTaskService taskService, ParentTaskService parentTaskService) {
        this.childTaskService = taskService;
        this.parentTaskService = parentTaskService;
    }

    @PostMapping("/child/new-task")
    public ResponseEntity saveNewTask(@RequestBody ChildNewTaskDto childNewTaskDto) {
        childTaskService.saveTask(childNewTaskDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/parent/new-task")
    public ResponseEntity saveNewTask(@RequestBody ParentNewTaskDto parentNewTaskDto) {
        parentTaskService.saveTask(parentNewTaskDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
