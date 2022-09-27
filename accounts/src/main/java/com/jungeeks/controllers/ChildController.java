package com.jungeeks.controllers;

import com.jungeeks.entitiy.FamilyTask;
import com.jungeeks.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/account/inf")
@RestController
@Log4j2
@RequiredArgsConstructor
public class ChildController {

    private final UserService userService;

    /*
    *  Get all ACTIVE tasks of child
    * */
    @GetMapping("/{id}")
    public List<FamilyTask> getNotifications(@PathVariable Long id) {
        return userService.getUserActiveTaskById(id);
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<FamilyTask>> getTasks(@PathVariable Long id) {
        System.out.println(new ResponseEntity<>(userService.getUserTaskById(id), HttpStatus.ACCEPTED));
        return null;
    }



}
