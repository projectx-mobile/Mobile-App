package com.jungeeks.service.photoStorage.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.jungeeks.service.photoStorage.enums.PHOTO_TYPE;
import com.jungeeks.service.photoStorage.PhotoStorageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Log4j2
public class AwsPhotoStorageServiceImpl implements PhotoStorageService {

    @Autowired
    private AmazonS3 s3;
    @Value("${PHOTO_BUCKET_NAME}")
    private String bucketName;


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
                    s3.putObject(bucketName, photoType.toString()+fileName, tmp);
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
            S3Object o = s3.getObject(bucketName, photoType.toString()+fileName);
            S3ObjectInputStream s3is = o.getObjectContent();
            File tmp = File.createTempFile("kidsAppTmpRead", "photo_tmp.jpg");
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
            s3.deleteObject(bucketName, photoType.toString()+fileName);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
        }
    }

    @Override
    public void update(String path, MultipartFile multipartFile, PHOTO_TYPE photoType) {
        delete(path,PHOTO_TYPE.USER);
        store(multipartFile,path,photoType);
    }
}
