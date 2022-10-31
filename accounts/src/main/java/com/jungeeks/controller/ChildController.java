package com.jungeeks.controller;

import com.jungeeks.entity.User;
import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.log4j.Log4j2;
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
 *
 * @author nevels 09.29.2022
 */
@RequestMapping("/child/")
@RestController
@Slf4j
public class ChildController {

    @Autowired
    @Qualifier("accounts_userServiceImpl")
    private UserService userService;
    @Autowired
    private AuthorizationService authorizationService;


    /**
     * Gets deadline of task.
     *
     * @return the deadline of task
     */
    @GetMapping("deadline")
    public ResponseEntity<List<NotificationResponse>> getDeadlineOfTask() {
        SecurityUserFirebase userDetails = authorizationService.getUser();
        User user = userService.getUserById(userDetails.getUid());
        return new ResponseEntity<>(userService.getDeadlineOfAllTask(user), HttpStatus.ACCEPTED);
    }


    /**
     * Gets tasks.
     *
     * @return the tasks
     */
    @GetMapping("tasks")
    public ResponseEntity<List<TaskResponse>> getTasks() {
        SecurityUserFirebase userDetails = authorizationService.getUser();
        User user = userService.getUserById(userDetails.getUid());
        return new ResponseEntity<>(userService.getUserTaskById(user), HttpStatus.ACCEPTED);
    }

}
