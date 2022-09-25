package com.jungeeks.controllers;

import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.services.dto.UserInfoService;
import com.jungeeks.services.storage.StorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;

@RequestMapping("/account/inf")
@RestController
@Log4j2
public class PersonalInfController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private StorageService storageService;
    @Autowired
    ApplicationContext applicationContext;

    /**
     * get personal info
     **/
    @PostMapping()
    public UserInfoDto getUserPersonalInfo(@RequestParam(required = true,name = "id") Long id) {
        return userInfoService.getUserInfoByUserId(id);
    }

    /**
     * get user photo by filename
     **/
    @PostMapping("/downloadPhoto")
    public ResponseEntity<FileSystemResource> downloadPhoto(@RequestParam(required = true, name = "path") String path) throws IOException {
        File photo = storageService.load(path);
        if (photo.exists() && photo.canRead()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new FileSystemResource(photo));
        } else {
            return null;
        }
    }

}
