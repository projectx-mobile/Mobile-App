package com.jungeeks.controller;

import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import com.jungeeks.service.dto.ChildService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for operations related to child home page:
 * operation on getting deadline of all tasks
 * operation getting data of all tasks
 */
@RequestMapping("/child")
@RestController
@Slf4j
public class ChildController {

    private final ChildService childService;

    @Autowired
    public ChildController(@Qualifier("accounts-childServiceImpl") ChildService childService) {
        this.childService = childService;
    }

    @GetMapping("/deadline")
    public ResponseEntity<List<NotificationDto>> getDeadlineOfTask() {
        return new ResponseEntity<>(childService.getDeadlineOfAllTask(), HttpStatus.OK);
    }

    @GetMapping("/tasks")
    public ResponseEntity<List<TaskDto>> getTasks() {
        return new ResponseEntity<>(childService.getUserTaskById(), HttpStatus.OK);
    }
}
