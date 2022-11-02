package com.jungeeks.controller;

import com.jungeeks.dto.FamilyIdDto;
import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.service.business.EditUserService;
import com.jungeeks.service.dto.UserInfoService;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Personal inf controller.
 */
@Slf4j
@RequestMapping("/account/info")
@RestController
public class PersonalInfoController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private EditUserService editUserService;

    @Autowired
    private AuthorizationService authorizationService;

    @GetMapping()
    public ResponseEntity<UserInfoDto> getPersonalInfo() {
        return ResponseEntity.ok(userInfoService.getCurrentUserInfo());
    }

    @PostMapping()
    public ResponseEntity<UserInfoDto> getPersonalInfo(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(userInfoService.getUserInfoByUserId(userId));
    }

    @PostMapping("/change-name")
    public ResponseEntity<String> changeUserName(@RequestParam(name = "name") String name){
        editUserService.changeUserName(name);
        return ResponseEntity.ok("Name changed");
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(){
        editUserService.changeUserStatus(USER_STATUS.REMOVED);
        return ResponseEntity.ok("Account deleted");
    }

    @PostMapping("/delete/member")
    public ResponseEntity<String> deleteFamilyMember(@RequestParam(name = "userId") Long userId){
        editUserService.deleteFamilyMember(userId);
        return ResponseEntity.ok("Account deleted");
    }

    @GetMapping("/family")
    public ResponseEntity<FamilyIdDto> getFamilyId(){
        return ResponseEntity.ok(userInfoService.getFamilyId());
    }
}
