package com.jungeeks.service.busines;

import org.springframework.web.multipart.MultipartFile;

public interface UploadUserService {
    void uploadPhoto(Long userId, MultipartFile multipartFile);
}
