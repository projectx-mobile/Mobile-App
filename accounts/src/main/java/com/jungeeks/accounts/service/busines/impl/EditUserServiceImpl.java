package com.jungeeks.accounts.service.busines.impl;

import com.jungeeks.accounts.service.entity.UserService;
import com.jungeeks.entity.User;
import com.jungeeks.accounts.service.busines.EditUserService;
import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import com.jungeeks.aws.service.photoStorage.enums.PHOTO_TYPE;
import com.jungeeks.security.service.AuthorizationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class EditUserServiceImpl implements EditUserService {

    private UserService userService;

    private PhotoStorageService photoStorageService;
    private AuthorizationService authorizationService;

    @Autowired
    @Qualifier("accountsUserService")
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setPhotoStorageService(PhotoStorageService photoStorageService) {
        this.photoStorageService = photoStorageService;
    }

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Override
    public void storeUserPhoto(Long photoId, @NonNull MultipartFile multipartFile) {
        String uid = authorizationService.getUser().getUid();
        User user = userService.getUserByUid(uid);
        String path = user.getPhoto().get(Math.toIntExact(photoId)).getPath();
        photoStorageService.update(path, multipartFile, PHOTO_TYPE.USER);
        log.debug("User photo uploaded");
    }
}
