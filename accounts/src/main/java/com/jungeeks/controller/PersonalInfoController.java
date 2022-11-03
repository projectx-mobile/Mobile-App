package com.jungeeks.controller;

import com.jungeeks.dto.FamilyIdDto;
import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.service.business.EditUserService;
import com.jungeeks.service.dto.UserInfoService;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The type Personal inf controller.
 */
@Slf4j
@RequestMapping("/account/info")
@RestController
public class PersonalInfoController {

    private final UserInfoService userInfoService;
    private final EditUserService editUserService;

    @Autowired
    public PersonalInfoController(UserInfoService userInfoService, EditUserService editUserService) {
        this.userInfoService = userInfoService;
        this.editUserService = editUserService;
    }

    @GetMapping()
    public ResponseEntity<UserInfoDto> getPersonalInfo() {
        return ResponseEntity.ok(userInfoService.getCurrentUserInfo());
    }

    @PostMapping()
    public ResponseEntity<UserInfoDto> getPersonalInfo(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(userInfoService.getUserInfoByUserId(userId));
    }

    @PutMapping("/change-name")
    public ResponseEntity<String> changeUserName(@RequestParam(name = "name") String name){
        editUserService.changeUserName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(){
        editUserService.changeUserStatus(USER_STATUS.REMOVED);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/member")
    public ResponseEntity<String> deleteFamilyMember(@RequestParam(name = "userId") Long userId){
        editUserService.deleteFamilyMember(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/family")
    public ResponseEntity<FamilyIdDto> getFamilyId(){
        return ResponseEntity.ok(userInfoService.getFamilyId());
    }
}
