package com.jungeeks.service.entity.impl;

import com.jungeeks.entitiy.User;
import com.jungeeks.exception.PathNotFoundException;
import com.jungeeks.exception.UserNotFoundException;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.service.photoStorage.PhotoStorageService;
import com.jungeeks.service.photoStorage.enums.PHOTO_TYPE;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private PhotoStorageService photoStorageService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setPhotoStorageService(PhotoStorageService photoStorageService) {
        this.photoStorageService = photoStorageService;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException(String.format("User with id %s not found", id)));
    }

    @Override
    public List<User> getAllByFamilyId(@NonNull String familyId) {
        return userRepository.findAllByFamilyId(familyId).orElseThrow(() -> new UserNotFoundException(String.format("Family id %s not found", familyId)));
    }

    @Override
    public File getUserPhoto(String path) {
        File photo = photoStorageService.load(path, PHOTO_TYPE.USER);
        if (photo.exists() && photo.canRead()) {
            return photo;
        } else {
            throw new PathNotFoundException("Photo not found");
        }
    }

}
