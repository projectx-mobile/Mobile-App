package com.jungeeks.aws.service.photoStorage;

import com.jungeeks.aws.service.photoStorage.enums.PHOTO_TYPE;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface PhotoStorageService {
    void store(MultipartFile file, String fileName, PHOTO_TYPE photoType);

    File load(String path, PHOTO_TYPE photoType);

    void delete(String fileName, PHOTO_TYPE photoType);

    void update(String path, MultipartFile file, PHOTO_TYPE photoType);
}
