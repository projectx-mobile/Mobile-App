package com.jungeeks.init;

import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class InitPhotoStorage {

    private final PhotoStorageService photoStorageService;

    @Autowired
    public InitPhotoStorage(PhotoStorageService photoStorageService) {
        this.photoStorageService = photoStorageService;
    }

    @PostConstruct
    private void postConstruct() {
        photoStorageService.storeDefaultPhoto();
    }

}
