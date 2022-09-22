package com.jungeeks.populators;

import com.jungeeks.entitiy.Photo;
import com.jungeeks.entitiy.User;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.services.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StorageService storageService;

    public void save(User user){
        List<Photo> photo = user.getPhoto();
        userRepository.save(user);
    }
}