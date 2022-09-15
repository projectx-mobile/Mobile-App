package com.jungeeks.controllers;

import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.entitiy.User;
import com.jungeeks.repository.UserRepository;
import com.jungeeks.services.dto.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/account/inf")
@RestController
public class PersonalInfController {

    @Autowired
    private UserInfoService userInfoService;
    /**
     * remove
     **/
    @PostMapping
    public UserInfoDto getUserPersonalInfo() {

        return userInfoService.getUserInfoByUserId(1L);
    }

//    @PostMapping("/downloadPhoto/{id}")
//    public ResponseEntity<FileSystemResource> getUserPhotoByUserId(@PathVariable Long id) {
//        String
//        return ResponseEntity.ok()
//    }

}
