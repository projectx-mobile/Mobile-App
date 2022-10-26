package com.jungeeks.accounts.service.entity.impl;

import com.jungeeks.entity.User;
import com.jungeeks.accounts.exception.PathNotFoundException;
import com.jungeeks.accounts.exception.UserNotFoundException;
import com.jungeeks.accounts.repository.UserRepository;
import com.jungeeks.accounts.service.entity.UserService;
import com.jungeeks.service.photoStorage.PhotoStorageService;
import com.jungeeks.service.photoStorage.enums.PHOTO_TYPE;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service("accountsUserService")
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private PhotoStorageService photoStorageService;
    @Autowired
    @Qualifier("accountsUserRepository")
    public void setUserRepository(UserRepository userRepository) {
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
