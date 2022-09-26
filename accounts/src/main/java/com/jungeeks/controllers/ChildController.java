package com.jungeeks.controllers;

import com.jungeeks.entitiy.FamilyTask;
import com.jungeeks.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/account/inf")
@RestController
@Log4j2
public class ChildController {

    @Autowired
    UserService userService;
    @GetMapping("/{id}")
    public List<FamilyTask> getNotifications(@PathVariable Long id) {
        return userService.getUserById(id);
    }

}
