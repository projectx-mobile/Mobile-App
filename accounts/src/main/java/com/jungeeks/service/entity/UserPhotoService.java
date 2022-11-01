package com.jungeeks.service.entity;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * The interface User photo service.
 */
public interface UserPhotoService {
    /**
     * Gets user photo.
     *
     * @param path the path
     * @return the user photo
     */
    File getUserPhoto(String path);

    /**
     * Gets user photo.
     *
     * @param userId the user id
     * @param path   the path
     * @return the user photo
     */
    File getUserPhoto(Long userId, String path);

    /**
     * Add user photo string.
     *
     * @param multipartFile the multipart file
     * @return the string
     */
    String addUserPhoto(MultipartFile multipartFile);

    /**
     * Update user photo.
     *
     * @param path          the path
     * @param multipartFile the multipart file
     */
    void updateUserPhoto(String path, MultipartFile multipartFile);

    /**
     * Delete user photo.
     *
     * @param path the path
     */
    void deleteUserPhoto(String path);
}
