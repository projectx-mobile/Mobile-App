package com.jungeeks.services.dto.impl;

import com.jungeeks.dto.FamilyMemberDto;
import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.dto.enums.SIGN_UP_TYPE;
import com.jungeeks.entitiy.User;
import com.jungeeks.services.dto.UserInfoService;
import com.jungeeks.services.entity.UserService;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    @Autowired
    private UserService userService;

    public UserInfoDto getUserInfoByUserId(Long id) {
        Optional<User> userDb = userService.getUserById(id);
        User user = userDb.orElse(new User());
        List<User> familyMembers = userService.getAllByFamilyId(user.getFamily().getId()).orElse(new ArrayList<>());
        List<FamilyMemberDto> users = familyMembers.stream()
                .map((x) -> (
                    FamilyMemberDto.builder()
                            .id(x.getId())
                            .username(x.getName())
                            .build())
                )
                .toList();

//        try {
////            File file = new File("/tst/testFile.txt");
////            file.createNewFile();
////            FileItem fileItem = new DiskFileItem("fieldName", Files.probeContentType(file.toPath()),false,file.getName(),(int) file.length(),file.getParentFile());
////
////            InputStream inputStream = new FileInputStream(file);
////            OutputStream outputStream = fileItem.getOutputStream();
////            IOUtils.copy(inputStream,outputStream);
////            MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
//        }catch (Exception e){
//
//        }




        return UserInfoDto.builder()
                .username(user.getName())
                .email(user.getEmail())
                .userStatus(user.getUser_status())
                .passwordLength(0L)
                .signUpType(
                        SIGN_UP_TYPE.APPLE//FIX this (get this value from SpringSecurity)
                )
                .familyMembers(users)
                .build();
    }

}