package com.jungeeks.services.entity;

import com.jungeeks.entitiy.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);

    Optional<List<User>> getAllByFamilyId(String familyId);

}
