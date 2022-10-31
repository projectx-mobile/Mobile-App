package com.jungeeks.service.entity;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_STATUS;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface UserService {
    User getUserById(Long id);

    User getUserByUid(String uId);

    List<User> getAllByFamilyId(String familyId);

    void changeUserStatus(String uId, USER_STATUS newUserStatus);

    void changeUserName(String uId, String newName);

}
