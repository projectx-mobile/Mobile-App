package com.jungeeks;

import com.jungeeks.response.NotificationResponse;
import com.jungeeks.response.TaskResponse;
import com.jungeeks.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for operations related to child home page:
 * operation on getting deadline of all tasks
 * operation getting data of all tasks
 * @author nevels 09.29.2022
 */
@RequestMapping("/account/inf")
@RestController
@Log4j2
@RequiredArgsConstructor
public class ChildController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<List<NotificationResponse>> getDeadlineOfTask(@PathVariable() Long id) {
        return new ResponseEntity<>(userService.getDeadlineOfAllTask(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<TaskResponse>> getTasks(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserTaskById(id), HttpStatus.ACCEPTED);
    }

}
