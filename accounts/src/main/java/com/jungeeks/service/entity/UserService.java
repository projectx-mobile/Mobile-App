package com.jungeeks.service.entity;

import com.jungeeks.entitiy.User;

import java.io.File;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(Long id);

    List<User> getAllByFamilyId(String familyId);

    File getUserPhoto(String path);

}
