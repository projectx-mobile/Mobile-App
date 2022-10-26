package com.jungeeks.accounts.service.busines.impl;

import com.jungeeks.accounts.service.entity.UserService;
import com.jungeeks.entity.User;
import com.jungeeks.accounts.service.busines.UploadUserService;
import com.jungeeks.service.photoStorage.PhotoStorageService;
import com.jungeeks.service.photoStorage.enums.PHOTO_TYPE;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Log4j2
public class UploadUserServiceImpl implements UploadUserService {

    private UserService userService;

    private PhotoStorageService photoStorageService;

    @Autowired
    @Qualifier("accountsUserService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPhotoStorageService(PhotoStorageService photoStorageService) {
        this.photoStorageService = photoStorageService;
    }

    @Override
    public void uploadPhoto(Long photoId, @NonNull MultipartFile multipartFile) {
        User user = userService.getUserById(1L);//fix get userId from Security
        String path = user.getPhoto().get(Math.toIntExact(photoId)).getPath();
        photoStorageService.update(path, multipartFile, PHOTO_TYPE.USER);
        log.debug("User photo uploaded");
    }
}
