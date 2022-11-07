package com.jungeeks.service.entity;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;

import java.util.List;


public interface UserService {

    User getUserById(Long id);

    User getUserByUid(String uId);

    List<User> getAllByFamilyId(String familyId);

    List<User> getAllByFamilyIdAndUserRoleAndUserStatus(String familyId, USER_ROLE user_role, USER_STATUS userStatus);

    boolean changeUserStatus(String uId, USER_STATUS newUserStatus);

    boolean changeUserName(String uId, String newName);

    boolean deleteFamilyMember(Long userId);

    List<User> getAllByFamilyIdAndUserRole(String familyId, USER_ROLE parent);

    List<User> getAllByFamilyIdAndUserRoleWithAdmin(String familyId, USER_ROLE userRole);
}
