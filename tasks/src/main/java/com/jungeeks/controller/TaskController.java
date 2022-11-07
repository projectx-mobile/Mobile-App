package com.jungeeks.controller;

import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.dto.ConfirmTaskDto;
import com.jungeeks.dto.ParentNewTaskDto;
import com.jungeeks.service.dto.ChildTaskService;
import com.jungeeks.service.dto.ParentTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/child/new")
    public ResponseEntity<HttpStatus> saveNewTask(@RequestBody ChildNewTaskDto childNewTaskDto) {
        childTaskService.saveTask(childNewTaskDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/parent/new")
    public ResponseEntity<HttpStatus> saveNewTask(@RequestBody ParentNewTaskDto parentNewTaskDto) {
        parentTaskService.saveTask(parentNewTaskDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/parent/confirm")
    public ResponseEntity<HttpStatus> confirmTask(@RequestBody ConfirmTaskDto confirmTaskDto) {
        parentTaskService.confirmTask(confirmTaskDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/parent/reject")
    public ResponseEntity<HttpStatus> rejectTask(@RequestParam Long taskId) {
        parentTaskService.rejectTask(taskId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
