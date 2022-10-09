package com.jungeeks.controller;

import com.jungeeks.entity.SecurityUserFirebase;
import com.jungeeks.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/getUser")
public class GetUserController {

    @Autowired
    private SecurityService securityService;

    @PostMapping
    public ResponseEntity<SecurityUserFirebase> get(){
        return ResponseEntity.status(HttpStatus.OK).body(securityService.getUser());
    }
}
