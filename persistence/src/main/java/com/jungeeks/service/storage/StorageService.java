package com.jungeeks.service.storage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface StorageService {
    void store(MultipartFile file) throws IOException;

    File load(String path) throws IOException;

    void delete(String filename);
}
