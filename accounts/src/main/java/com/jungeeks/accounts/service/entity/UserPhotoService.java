package com.jungeeks.accounts.service.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UserPhotoService {
    File getUserPhoto(String path);

    File getUserPhoto(Long userId, String path);

    String addUserPhoto(MultipartFile multipartFile);

    void updateUserPhoto(String path, MultipartFile multipartFile);

    void deleteUserPhoto(String path);
}
