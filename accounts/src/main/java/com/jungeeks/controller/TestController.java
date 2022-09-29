package com.jungeeks.controller;

import com.jungeeks.service.photoStorage.enums.PHOTO_TYPE;
import com.jungeeks.service.photoStorage.PhotoStorageService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequestMapping("/tst")
@RestController
@Log4j2
public class TestController {

    @Autowired
    private PhotoStorageService photoStorageService;

    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@RequestParam(name = "photo") MultipartFile multipartFile) throws IOException {
        photoStorageService.store(multipartFile,"tstUserPhoto.jpg", PHOTO_TYPE.USER);
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
