package com.jungeeks.accounts.controller;

import com.jungeeks.accounts.dto.UserInfoDto;
import com.jungeeks.accounts.service.dto.UserInfoService;
import com.jungeeks.accounts.service.entity.UserService;
import com.jungeeks.accounts.service.busines.UploadUserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping("/account/inf")
@RestController
@Log4j2
public class PersonalInfController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UploadUserService uploadUserService;
    @Autowired
    @Qualifier("accountsUserService")
    private UserService userService;

    /**
     * get personal info
     **/
    @GetMapping()
    public ResponseEntity<UserInfoDto> getUserPersonalInfo() {

        return ResponseEntity.ok(userInfoService.getUserInfoByUserId(1L));//add get user id with security credentials
    }

    /**
     * update user photo
     **/
    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@RequestParam(name = "photo") MultipartFile multipartFile) throws IOException {
        uploadUserService.uploadPhoto(0L, multipartFile);
        return ResponseEntity.ok().body("Photo uploaded");
    }

    /**
     * get user photo by path
     **/
    @PostMapping("/downloadPhoto")
    public ResponseEntity<FileSystemResource> downloadPhoto(@RequestParam(name = "path") String path) throws IOException {
        File photo = userService.getUserPhoto(path);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(photo));
    }

}
