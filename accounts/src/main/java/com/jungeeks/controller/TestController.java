package com.jungeeks.controller;

import com.jungeeks.aws.service.photoStorage.enums.PHOTO_TYPE;
import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
@Slf4j
@RequestMapping("/test")
@RestController
public class TestController {

    @Autowired
    private PhotoStorageService photoStorageService;

    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@RequestParam(name = "photo") MultipartFile multipartFile) throws IOException {
        photoStorageService.store(multipartFile,"default_account_photo.jpeg", PHOTO_TYPE.USER);
        return ResponseEntity.ok().body("file unloaded");
    }

    @PostMapping("/downloadPhoto")
    public ResponseEntity<FileSystemResource> downloadPhoto(@RequestParam(name = "path") String path) throws IOException {
        File photo = photoStorageService.load(path,PHOTO_TYPE.USER);
        if (photo.exists() && photo.canRead()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(new FileSystemResource(photo));
        } else {
            return null;
        }
    }
}
