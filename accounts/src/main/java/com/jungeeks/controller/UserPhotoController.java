package com.jungeeks.controller;

import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import com.jungeeks.service.entity.UserPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Objects;

@RequestMapping("/account/info/photo")
@RestController
@Slf4j
public class UserPhotoController {

    private final UserPhotoService userPhotoService;

    @Autowired
    public UserPhotoController(UserPhotoService userPhotoService) {
        this.userPhotoService = userPhotoService;
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUserPhoto(@RequestParam String path,
                                                  @RequestParam MultipartFile photo) {
        userPhotoService.updateUserPhoto(path, photo);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/get-by-path")
    public ResponseEntity<FileSystemResource> getUserPhoto(@RequestParam(name = "path") String path,
                                                           @RequestParam(name = "userId", required = false) Long userId) {
        File userPhoto;
        if (Objects.isNull(userId)) {
            userPhoto = userPhotoService.getUserPhoto(path);
        } else {
            userPhoto = userPhotoService.getUserPhoto(userId, path);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(userPhoto));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUserPhoto(@RequestParam(name = "path") String path) {
        userPhotoService.deleteUserPhoto(path);
        return ResponseEntity.ok()
                .body("photo success deleted");
    }
}
