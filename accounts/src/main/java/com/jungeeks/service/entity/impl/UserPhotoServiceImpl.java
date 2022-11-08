package com.jungeeks.service.entity.impl;

import com.jungeeks.exception.BusinessException;
import com.jungeeks.exception.enums.ERROR_CODE;
import com.jungeeks.service.business.impl.RegisterUserServiceImpl;
import com.jungeeks.service.entity.UserPhotoService;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import com.jungeeks.aws.service.photoStorage.enums.PHOTO_TYPE;
import com.jungeeks.entity.Photo;
import com.jungeeks.entity.User;
import com.jungeeks.security.service.AuthorizationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service("accounts-userPhotoServiceImpl")
public class UserPhotoServiceImpl implements UserPhotoService {

    private final UserService userService;
    private final PhotoStorageService photoStorageService;
    private final AuthorizationService authorizationService;

    public UserPhotoServiceImpl(@Qualifier("accounts-userServiceImpl") UserService userService,
                                PhotoStorageService photoStorageService,
                                AuthorizationService authorizationService) {
        this.userService = userService;
        this.photoStorageService = photoStorageService;
        this.authorizationService = authorizationService;
    }

    @Override
    public File getUserPhoto(String path) {
        User user = userService.getUserByUid(getUid());
        return getFile(path, user);
    }

    @Override
    public File getUserPhoto(Long userId, String path) {
        User user = userService.getUserById(userId);
        return getFile(path, user);
    }

    @Transactional
    @Override
    public String addUserPhoto(@NonNull MultipartFile multipartFile) {
        User user = userService.getUserByUid(getUid());
        List<Photo> photo = user.getPhoto();
        if (Objects.isNull(photo)) {
            photo = new ArrayList<>();
        }
        String path = user.getId() + "_" + user.getPhoto().size() + ".jpeg";
        photo.set(0,Photo.builder()
                .path(path)
                .creationDate(LocalDateTime.now())
                .build());
        photoStorageService.store(multipartFile, path, PHOTO_TYPE.USER);
        return path;
    }

    @Transactional
    @Modifying
    @Override
    public void updateUserPhoto(String path, MultipartFile multipartFile) {
        User user = userService.getUserByUid(getUid());
        List<Photo> photos = user.getPhoto();
        Photo photo = photos.stream()
                .filter(x -> x.getPath().equals(path))
                .findFirst()
                .orElseThrow(() -> new BusinessException((String.format("This path %s not found", path)),
                        ERROR_CODE.PATH_NOT_FOUND,
                        HttpStatus.NOT_FOUND));
        addUserPhoto(multipartFile);
        if (! (photos.size() == 1 && photo.getPath().equals(RegisterUserServiceImpl.DEFAULT_PHOTO_PATH))) {
            photoStorageService.update(photo.getPath(), multipartFile, PHOTO_TYPE.USER);
        }
        photo.setCreationDate(LocalDateTime.now());
    }

    @Transactional
    @Modifying
    @Override
    public void deleteUserPhoto(String path) {
        User user = userService.getUserByUid(getUid());
        List<Photo> userPhotos = user.getPhoto();
        Photo photo = userPhotos.stream()
                .filter(x -> x.getPath().equals(path))
                .findFirst()
                .orElseThrow(() -> new BusinessException((String.format("This path %s not found", path)),
                        ERROR_CODE.PATH_NOT_FOUND, HttpStatus.NOT_FOUND));
        if (userPhotos.size() == 1) {
            userPhotos.set(0, Photo.builder()
                    .path(RegisterUserServiceImpl.DEFAULT_PHOTO_PATH)
                    .creationDate(LocalDateTime.now())
                    .build());
        } else {
            userPhotos.remove(photo);
            photoStorageService.delete(photo.getPath(), PHOTO_TYPE.USER);
        }
    }


    private File getFile(String path, User user) {
        Photo photo = user.getPhoto().stream()
                .filter(x -> x.getPath().equals(path))
                .findFirst()
                .orElseThrow(() -> new BusinessException((String.format("This path %s not found", path)),
                        ERROR_CODE.PATH_NOT_FOUND, HttpStatus.NOT_FOUND));

        File tmpPhoto = photoStorageService.load(photo.getPath(), PHOTO_TYPE.USER);
        if (tmpPhoto.exists() && tmpPhoto.canRead()) {
            return tmpPhoto;
        } else {
            throw new BusinessException("Photo not found", HttpStatus.NOT_FOUND);
        }
    }

    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
