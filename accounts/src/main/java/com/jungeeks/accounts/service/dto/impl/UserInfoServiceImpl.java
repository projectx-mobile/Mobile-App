package com.jungeeks.accounts.service.dto.impl;

import com.jungeeks.accounts.service.entity.UserService;
import com.jungeeks.accounts.dto.FamilyMemberDto;
import com.jungeeks.accounts.dto.UserInfoDto;
import com.jungeeks.entity.User;
import com.jungeeks.accounts.service.dto.FamilyMemberService;
import com.jungeeks.accounts.service.dto.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserService userService;
    private FamilyMemberService familyMemberService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setFamilyMemberService(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    public UserInfoDto getUserInfoByUserId(Long id) {
        User user = userService.getUserById(id);
        List<FamilyMemberDto> familyMembers = familyMemberService.getFamilyMembers(user.getFamily().getId());
        return UserInfoDto.builder()
                .username(user.getName())
//                .email(user.getEmail())
//                .userStatus(user.getUser_status())
//                .signUpType(
//                        SIGN_UP_TYPE.APPLE//FIX this (get this value from SpringSecurity)
//                )
                .familyMembers(familyMembers)
//                .photoPath(user.getPhoto().get(0).getPath())
                .build();
    }

}