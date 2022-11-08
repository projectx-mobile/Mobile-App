package com.jungeeks.controller;

import com.jungeeks.dto.TaskDto;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.FamilyTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/account/tasks")
public class FamilyTaskController {
    @Autowired
    private FamilyTaskService familyTaskService;

    @GetMapping()
    public ResponseEntity<List<TaskDto>> getFamilyTasks() {
        return ResponseEntity.ok(familyTaskService.getAllTasksByCurrentUserUid());
    }

    @PostMapping()
    public ResponseEntity<List<TaskDto>> getFamilyTasks(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(familyTaskService.getAllTasksByUserId(userId));
    }
}
