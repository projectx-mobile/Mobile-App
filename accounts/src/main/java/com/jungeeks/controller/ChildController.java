package com.jungeeks.controller;

import com.jungeeks.entity.User;
import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.entity.UserService;
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

    @Autowired
    @Qualifier("accounts_userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("utils_authorizationServiceImpl")
    private AuthorizationService authorizationService;

    @GetMapping("deadline")
    public ResponseEntity<List<NotificationDto>> getDeadlineOfTask() {
        SecurityUserFirebase userDetails = authorizationService.getUser();
        User user = userService.getUserByUid(userDetails.getUid());//TODO: move to childService
        return new ResponseEntity<>(userService.getDeadlineOfAllTask(user), HttpStatus.ACCEPTED);
    }

    @GetMapping("tasks")
    public ResponseEntity<List<TaskDto>> getTasks() {
        SecurityUserFirebase userDetails = authorizationService.getUser();
        User user = userService.getUserByUid(userDetails.getUid());//TODO: move to childService
        return new ResponseEntity<>(userService.getUserTaskById(user), HttpStatus.ACCEPTED);
    }
}
