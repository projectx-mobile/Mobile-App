package com.jungeeks.accounts.service.entity;

import com.jungeeks.entity.User;

import java.io.File;
import java.util.List;

public interface UserService {
    User getUserById(Long id);

    List<User> getAllByFamilyId(String familyId);

    File getUserPhoto(String path);

}
