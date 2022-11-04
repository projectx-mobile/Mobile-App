package com.jungeeks.controller;

import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.service.business.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegisterController {

    private final RegisterUserService registerUserService;

    @Autowired
    public RegisterController(RegisterUserService registerUserService) {
        this.registerUserService = registerUserService;
    }

    @PostMapping("/parent")
    public ResponseEntity<String> registerParent(@RequestParam(name = "username") String username) {
        registerUserService.registerParentUser(username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/invite/parent")
    public ResponseEntity<String> registerParentByInvite(@RequestParam(name = "username") String username,
                                                         @RequestParam(name = "familyId") String familyId) {
        registerUserService.registerByInvite(username, familyId, USER_ROLE.PARENT);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/invite/child")
    public ResponseEntity<String> registerChildByInvite(@RequestParam(name = "username") String username,
                                                        @RequestParam(name = "familyId") String familyId) {
        registerUserService.registerByInvite(username, familyId, USER_ROLE.CHILD);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
