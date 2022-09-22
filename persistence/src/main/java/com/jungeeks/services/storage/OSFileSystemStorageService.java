package com.jungeeks.services.storage;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Log4j2
public class OSFileSystemStorageService implements StorageService {
    @Autowired
    private ApplicationContext applicationContext;
    @Value("${kids_app.resources.files.base_location}")
    private String baseLocation;

    @Override
    public void store(MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        File storedFile = new File(baseLocation + file.getOriginalFilename());
        boolean created = storedFile.createNewFile();
        if (created) {
            long l = inputStream.transferTo(new FileOutputStream(storedFile));
            log.debug("successfully create file at path: " + storedFile + " length:" + l);
        }
    }

    @Override
    public File load(String path) throws IOException {
        String fullPath = baseLocation + path;
        File file = new File(fullPath);
        if (file.exists()) {
            log.debug("file exist at: " + file +
                    "file length: " + file.length());
            return file;
        } else {
            log.warn("file not found at path: " + file);
            Resource resource = applicationContext.getResource("classpath:/images/photo_mock.jpg");
            File mockPhoto = File.createTempFile("kidsAppTmp", "photo_mock.jpg");
            log.debug("File: " + mockPhoto);
            InputStream inputStream = resource.getInputStream();
            long l = inputStream.transferTo(new FileOutputStream(mockPhoto));
            log.debug("file length: " + l);
            boolean exists = mockPhoto.exists();
            log.debug("file exist?: " + exists);
            if (exists) {
                return mockPhoto;
            }
        }
        return null;
    }

    @Override
    public void delete(String filename) {
        File file = new File(baseLocation + filename);
        boolean deleted = file.delete();
        if (deleted) {
            log.debug("successfully deleted file at path: " + file);
        }
    }
}
