package com.jungeeks.controller;

import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.service.dto.ParentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    private final ParentService parentService;

    @Autowired
    public UserController(@Qualifier("accounts-parentServiceImpl") ParentService parentService) {
        this.parentService = parentService;
    }

    @GetMapping("/getParentHome")
    public ResponseEntity<ParentHomeDto> getDataForParentHomePage() {
        return new ResponseEntity<>(parentService.getParentHomeDate(), HttpStatus.OK);
    }
}
