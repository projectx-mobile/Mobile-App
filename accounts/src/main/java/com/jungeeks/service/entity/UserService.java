package com.jungeeks.service.entity;

import com.jungeeks.entity.User;
import com.jungeeks.dto.NotificationDto;
import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;

import java.util.List;


public interface UserService {

    User getUserById(Long id);

    User getUserByUid(String uId);

    List<User> getAllByFamilyId(String familyId);

    List<User> getAllByFamilyIdAndUserRole(String familyId, USER_ROLE user_role);

    void changeUserStatus(String uId, USER_STATUS newUserStatus);

    void changeUserName(String uId, String newName);

    void deleteFamilyMember(Long userId);
}
