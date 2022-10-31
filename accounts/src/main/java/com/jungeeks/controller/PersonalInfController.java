package com.jungeeks.controller;

import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.service.busines.EditUserService;
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
@RequestMapping("/account/inf")
@RestController
public class PersonalInfController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private EditUserService editUserService;
    @Autowired
    private AuthorizationService authorizationService;

    /**
     * Gets personal info.
     *
     * @return the personal info
     */
    @GetMapping()
    public ResponseEntity<UserInfoDto> getPersonalInfo() {
        String uid = authorizationService.getUser().getUid();
        return ResponseEntity.ok(userInfoService.getUserInfoByUserUId(uid));
    }

    /**
     * Gets personal info.
     *
     * @param userId the user id
     * @return the personal info
     */
    @PostMapping()
    public ResponseEntity<UserInfoDto> getPersonalInfo(@RequestParam(name = "userId") Long userId) {
        return ResponseEntity.ok(userInfoService.getUserInfoByUserId(userId));
    }

    /**
     * Change user name response entity.
     *
     * @param name the name
     * @return the response entity
     */
    @PostMapping("/change-name")
    public ResponseEntity<String> changeUserName(@RequestParam(name = "name") String name){
        editUserService.changeUserName(name);
        return ResponseEntity.ok("Name changed");
    }

    /**
     * Delete user response entity.
     *
     * @return the response entity
     */
    @PostMapping("/delete")
    public ResponseEntity<String> deleteUser(){
        editUserService.changeUserStatus(USER_STATUS.REMOVED);
        return ResponseEntity.ok("Account deleted");
    }

    /**
     * Delete user response entity.
     *
     * @param userId the user id
     * @return the response entity
     */
    @PostMapping("/delete/member")
    public ResponseEntity<String> deleteUser(@RequestParam(name = "userId") Long userId){

        return ResponseEntity.ok("Account deleted");
    }

}
