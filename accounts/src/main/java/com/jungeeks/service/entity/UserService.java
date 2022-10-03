package com.jungeeks.service.entity;

import com.jungeeks.dto.ParentHomeDto;
import com.jungeeks.entitiy.User;
import com.jungeeks.entitiy.enums.USER_ROLE;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User getUserById(Long id);

    List<User> getAllByFamilyId(String familyId);

    List<User> getAllByFamilyIdAndUserRole(String familyId, USER_ROLE user_role);
}
