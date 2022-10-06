package com.jungeeks.service.busines;

import lombok.NonNull;
import org.springframework.web.multipart.MultipartFile;

public interface UploadUserService {
    void uploadPhoto(Long photoId, @NonNull MultipartFile multipartFile);
}
