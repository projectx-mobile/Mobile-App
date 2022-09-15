package com.jungeeks.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
public class OSFileSystemStorageService implements StorageService {

    private String location;

    @Override
    public void store(MultipartFile file) {

    }

    @Override
    public File load(String filename) {
        return null;
    }

    @Override
    public void delete(String filename) {

    }
}
