package com.jungeeks.aws.service.photoStorage.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import com.jungeeks.aws.service.photoStorage.enums.PHOTO_TYPE;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Objects;

@Service
@Slf4j
public class AwsPhotoStorageServiceImpl implements PhotoStorageService {

    @Autowired
    private AmazonS3 s3;
    @Value("${PHOTO_BUCKET_NAME}")
    private String bucketName;

    @Value("classpath:static/images/photo_mock.jpg")
    Resource defaultPhoto;

    @Override
    public void store(MultipartFile file, String fileName, PHOTO_TYPE photoType) {

        try {
            File tmp = File.createTempFile("kidsAppTmp", fileName);
            boolean exists = tmp.exists();
            if (exists) {
                log.debug("Create tmp file: " + tmp);
                long l = file.getInputStream().transferTo(new FileOutputStream(tmp));
                log.debug("file length: " + l);
                try {
                    s3.putObject(bucketName, photoType.toString() + fileName, tmp);
                } catch (AmazonServiceException e) {
                    log.error(e.getErrorMessage());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File load(String fileName, PHOTO_TYPE photoType) {
        try {
            S3Object o = s3.getObject(bucketName, photoType.toString() + fileName);
            S3ObjectInputStream s3is = o.getObjectContent();
            File tmp = File.createTempFile("kidsAppTmpRead", fileName + ".jpg");
            FileOutputStream fos = new FileOutputStream(tmp);
            byte[] read_buf = new byte[1024];
            int read_len = 0;
            while ((read_len = s3is.read(read_buf)) > 0) {
                fos.write(read_buf, 0, read_len);
            }
            s3is.close();
            fos.close();
            return tmp;
        } catch (AmazonServiceException e) {
            log.error(e.getErrorMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public void delete(String fileName, PHOTO_TYPE photoType) {
        try {
            s3.deleteObject(bucketName, photoType.toString() + fileName);
        } catch (AmazonServiceException e) {
            log.error(e.getErrorMessage());
        }
    }

    @Override
    public void update(String path, MultipartFile multipartFile, PHOTO_TYPE photoType) {
        delete(path, PHOTO_TYPE.USER);
        store(multipartFile, path, photoType);
    }

    @Override
    public String storeDefaultPhoto() {
        String path = PHOTO_TYPE.USER.toString() + "default_account_photo.jpeg";
        try {
            S3Object o = s3.getObject(bucketName, path);
        }catch (AmazonServiceException amazonServiceException){
            try {
                InputStream inputStream = defaultPhoto.getInputStream();
                File tmp = File.createTempFile("kidsAppTmp", "default_account_photo.jpeg");
                inputStream.transferTo(new FileOutputStream(tmp));
                try {
                    s3.putObject(bucketName, path, tmp);
                } catch (AmazonServiceException e) {
                    log.error(e.getErrorMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return path;
    }
}
