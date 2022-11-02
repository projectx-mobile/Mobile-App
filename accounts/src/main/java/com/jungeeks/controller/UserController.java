package com.jungeeks.controller;

import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entity.User;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.dto.ParentService;
import com.jungeeks.service.entity.UserService;
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

    @Autowired
    @Qualifier("accounts_parentServiceImpl")
    private ParentService parentService;

    @Autowired
    @Qualifier("accounts_userServiceImpl")
    private UserService userService;

    @GetMapping("/getParentHome")
    public ResponseEntity<ParentHomeDto> getDataForParentHomePage() {
        return new ResponseEntity<>(parentService.getParentHomeDate(), HttpStatus.OK);
    }
}
