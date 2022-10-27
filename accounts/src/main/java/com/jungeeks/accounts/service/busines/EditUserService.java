package com.jungeeks.accounts.service.busines;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface EditUserService {
    void storeUserPhoto(Long photoId, @NonNull MultipartFile multipartFile);

}
