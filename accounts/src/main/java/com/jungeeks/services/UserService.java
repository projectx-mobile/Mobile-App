package com.jungeeks.services;

import com.jungeeks.entitiy.FamilyTask;
import com.jungeeks.entitiy.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<FamilyTask> getUserById(Long id);

    Optional<List<User>> getAllByFamilyId(String familyId);

}
