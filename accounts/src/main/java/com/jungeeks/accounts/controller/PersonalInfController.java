package com.jungeeks.accounts.controller;

import com.jungeeks.accounts.dto.UserInfoDto;
import com.jungeeks.accounts.service.dto.UserInfoService;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/account/inf")
@RestController
public class PersonalInfController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping()
    public ResponseEntity<UserInfoDto> getAuthUserPersonalInfo() {
        String uid = authorizationService.getUser().getUid();
        return ResponseEntity.ok(userInfoService.getUserInfoByUserUId(uid));
    }

    @PostMapping()
    public ResponseEntity<UserInfoDto> getAuthUserPersonalInfo(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(userInfoService.getUserInfoByUserId(userId));
    }

}
