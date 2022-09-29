package com.jungeeks.controller;

import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.service.busines.UploadUserService;
import com.jungeeks.service.dto.UserInfoService;
import com.jungeeks.service.entity.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService userService;

    /**
     * get personal info
     **/
    @GetMapping()
    public UserInfoDto getUserPersonalInfo(@RequestParam(required = true, name = "id") Long id) {
        return userInfoService.getUserInfoByUserId(id);//add get user id with security credentials
    }

    /**
     * set user photo by path
     **/
    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@RequestParam(name = "photo") MultipartFile multipartFile) throws IOException {
        uploadUserService.uploadPhoto(1L, multipartFile);//add get user id with security credentials
        return ResponseEntity.ok().body("Photo unloaded");
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
