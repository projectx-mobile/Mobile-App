package com.jungeeks.services.storage.impl;

import com.jungeeks.services.storage.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public class OSFileSystemStorageService implements StorageService {

    private String location;

    @Override
    public void store(MultipartFile file) {

    }

    @Override
    public Path load(String filename) {
        return null;
    }

    @Override
    public Resource loadAsResource(String filename) {

        return null;
    }

    @Override
    public void delete(String filename) {

    }
}
