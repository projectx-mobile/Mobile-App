package com.jungeeks.controllers;

import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.services.dto.UserInfoService;
import com.jungeeks.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RequestMapping("/account/inf")
@RestController
public class PersonalInfController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private StorageService storageService;

    /**
     * get personal info
     **/
    @PostMapping()
    public UserInfoDto getUserPersonalInfo() {

        return userInfoService.getUserInfoByUserId(1L);
    }

    /**
     * get user photo by filename
     **/
    @PostMapping("/downloadPhoto")
    public ResponseEntity<FileSystemResource> downloadPhoto(@RequestParam(required = true, name = "photoFileName") String photoFileName) {
        File photo = storageService.load(photoFileName);
        if (photo.exists() && photo.canRead()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new FileSystemResource(photo));
        } else {
            return null;
        }
    }

}
