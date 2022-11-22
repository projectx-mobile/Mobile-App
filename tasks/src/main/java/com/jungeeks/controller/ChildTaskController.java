package com.jungeeks.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jungeeks.dto.ChildNewTaskDto;
import com.jungeeks.dto.GetTaskDto;
import com.jungeeks.service.dto.ChildTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/task/child")
@RestController
@Slf4j
public class ChildTaskController {

    private final ChildTaskService childTaskService;

    @Autowired
    public ChildTaskController(ChildTaskService childTaskService) {
        this.childTaskService = childTaskService;
    }

    @PostMapping("/new")
    public ResponseEntity<HttpStatus> saveNewTask(@RequestBody ChildNewTaskDto childNewTaskDto) {
        childTaskService.saveTask(childNewTaskDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-by-date")
    public ResponseEntity<List<GetTaskDto>> getTasksByDate(@RequestParam @JsonFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return new ResponseEntity<>(childTaskService.getTasksByDate(date), HttpStatus.OK);
    }
}
