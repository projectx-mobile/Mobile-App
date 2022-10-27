package com.jungeeks.accounts.controller;

import com.jungeeks.accounts.service.entity.UserPhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RequestMapping("/account/inf/photo")
@RestController
@Slf4j
public class UserPhotoController {

    @Autowired
    private UserPhotoService userPhotoService;

    @PostMapping("/photo/add")
    public ResponseEntity<String> addUserPhoto(@RequestParam(name = "photo") MultipartFile multipartFile) {
        userPhotoService.addUserPhoto(multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body("photo success added");
    }

    @PostMapping("/photo/update")
    public ResponseEntity<String> updateUserPhoto(@RequestParam(name = "path") String path,
                                                  @RequestParam(name = "photo") MultipartFile multipartFile) {
        userPhotoService.updateUserPhoto(path, multipartFile);
        return ResponseEntity.status(HttpStatus.OK).body("photo success updated");
    }

    @PostMapping("/photo/get_by_path")
    public ResponseEntity<FileSystemResource> getUserPhoto(@RequestParam(name = "path") String path) {
        File userPhoto = userPhotoService.getUserPhoto(path);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(userPhoto));
    }

    @PostMapping("/photo/get_by_userId_path")
    public ResponseEntity<FileSystemResource> getUserPhoto(@RequestParam(name = "userId") Long userId,
                                                           @RequestParam(name = "path") String path) {
        File userPhoto = userPhotoService.getUserPhoto(userId, path);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(new FileSystemResource(userPhoto));
    }

    @PostMapping("/photo/delete")
    public ResponseEntity<String> deleteUserPhoto(@RequestParam(name = "path") String path) {
        userPhotoService.deleteUserPhoto(path);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body("photo success deleted");
    }
}
