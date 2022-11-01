package com.jungeeks.controller;

import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.service.business.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/registration")
public class RegisterController {

    @Autowired
    private RegisterUserService registerUserService;

    /**
     * Register parent with email or google
     * Create family and setup other entities
     */
    @PostMapping("/parent")
    public ResponseEntity<String> registerParent(@RequestParam(name = "username") String username) {
        registerUserService.registerParentUser(username);
        return ResponseEntity.ok("User registered");
    }

    /**
     * Create family and setup other entities
     */
    @PostMapping("/invite/parent")
    public ResponseEntity<String> registerParentByInvite(@RequestParam(name = "username") String username,
                                                         @RequestParam(name = "familyId") String familyId) {
        registerUserService.registerByInvite(username, familyId, USER_ROLE.PARENT);
        return ResponseEntity.ok("User registered");
    }

    /**
     * add User to
     */
    @PostMapping("/invite/child")
    public ResponseEntity<String> registerChildByInvite(@RequestParam(name = "username") String username,
                                                        @RequestParam(name = "familyId") String familyId) {
        registerUserService.registerByInvite(username, familyId, USER_ROLE.CHILD);
        return ResponseEntity.ok("User registered");
    }
}
