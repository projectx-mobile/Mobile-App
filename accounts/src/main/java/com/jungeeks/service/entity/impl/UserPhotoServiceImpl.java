package com.jungeeks.service.entity.impl;

import com.jungeeks.exception.PathNotFoundException;
import com.jungeeks.service.entity.UserPhotoService;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.aws.service.photoStorage.PhotoStorageService;
import com.jungeeks.aws.service.photoStorage.enums.PHOTO_TYPE;
import com.jungeeks.entity.Photo;
import com.jungeeks.entity.User;
import com.jungeeks.security.service.AuthorizationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type User photo service.
 */
@Slf4j
@Service
public class UserPhotoServiceImpl implements UserPhotoService {

    private UserService userService;
    private PhotoStorageService photoStorageService;
    private AuthorizationService authorizationService;

    /**
     * Sets photo storage service.
     *
     * @param photoStorageService the photo storage service
     */
    @Autowired
    public void setPhotoStorageService(PhotoStorageService photoStorageService) {
        this.photoStorageService = photoStorageService;
    }

    /**
     * Sets authorization service.
     *
     * @param authorizationService the authorization service
     */
    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    /**
     * Sets user service.
     *
     * @param userService the user service
     */
    @Autowired
    public void setUserService(@Qualifier("accountsUserService") UserService userService) {
        this.userService = userService;
    }

    /**
     * Gets user photo.
     *
     * @param path the path
     * @return the user photo
     */
    @Override
    public File getUserPhoto(String path) {
        String uid = getUid();
        User user = userService.getUserByUid(uid);
        return getFile(path, user);
    }

    /**
     * Gets user photo.
     *
     * @param userId the user id
     * @param path   the path
     * @return the user photo
     */
    @Override
    public File getUserPhoto(Long userId, String path) {
        User user = userService.getUserById(userId);
        return getFile(path, user);
    }

    /**
     * Add user photo string.
     *
     * @param multipartFile the multipart file
     * @return the string
     */
    @Transactional
    @Override
    public String addUserPhoto(@NonNull MultipartFile multipartFile) {
        String uid = getUid();
        User user = userService.getUserByUid(uid);
        List<Photo> photo = user.getPhoto();
        if (Objects.isNull(photo)) {
            photo = new ArrayList<>();
        }
        String path = user.getId() + "_" + user.getPhoto().size() + ".jpeg";
        photo.add(Photo.builder()
                .path(path)
                .creationDate(LocalDateTime.now())
                .build());
        photoStorageService.store(multipartFile, path, PHOTO_TYPE.USER);
        return path;
    }

    /**
     * Update user photo.
     *
     * @param path          the path
     * @param multipartFile the multipart file
     */
    @Transactional
    @Modifying
    @Override
    public void updateUserPhoto(String path, MultipartFile multipartFile) {
        String uid = getUid();
        User user = userService.getUserByUid(uid);
        Photo photo = user.getPhoto().stream()
                .filter(x -> x.getPath().equals(path))
                .findFirst()
                .orElseThrow(() -> new PathNotFoundException(String.format("This path %s not found", path)));
        photo.setCreationDate(LocalDateTime.now());
        photoStorageService.update(photo.getPath(), multipartFile, PHOTO_TYPE.USER);
    }

    /**
     * Delete user photo.
     *
     * @param path the path
     */
    @Transactional
    @Modifying
    @Override
    public void deleteUserPhoto(String path) {
        String uid = getUid();
        User user = userService.getUserByUid(uid);
        List<Photo> userPhotos = user.getPhoto();
        Photo photo = userPhotos.stream()
                .filter(x -> x.getPath().equals(path))
                .findFirst()
                .orElseThrow(() -> new PathNotFoundException(String.format("This path %s not found", path)));
        userPhotos.remove(photo);
        photoStorageService.delete(photo.getPath(), PHOTO_TYPE.USER);
    }


    private File getFile(String path, User user) {
        Photo photo = user.getPhoto().stream()
                .filter(x -> x.getPath().equals(path))
                .findFirst()
                .orElseThrow(() -> new PathNotFoundException(String.format("This path %s not found", path)));
        File tmpPhoto = photoStorageService.load(photo.getPath(), PHOTO_TYPE.USER);
        if (tmpPhoto.exists() && tmpPhoto.canRead()) {
            return tmpPhoto;
        } else {
            throw new PathNotFoundException("Photo not found");
        }
    }
    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
