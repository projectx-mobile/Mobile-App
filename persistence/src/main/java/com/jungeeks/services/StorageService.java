package com.jungeeks.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;

public interface StorageService {
    void store(MultipartFile file);

    File load(String filename);

    void delete(String filename);
}
