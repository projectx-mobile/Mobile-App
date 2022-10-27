package com.jungeeks.accounts.service.entity;

import com.jungeeks.entity.User;
import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface UserService {
    User getUserById(Long id);

    User getUserByUid(String uId);

    List<User> getAllByFamilyId(String familyId);

}
