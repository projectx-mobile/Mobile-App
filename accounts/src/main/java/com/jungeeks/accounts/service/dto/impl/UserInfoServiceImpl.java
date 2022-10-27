package com.jungeeks.accounts.service.dto.impl;

import com.jungeeks.accounts.exception.InvalidRequestException;
import com.jungeeks.accounts.service.entity.UserService;
import com.jungeeks.accounts.dto.FamilyMemberDto;
import com.jungeeks.accounts.dto.UserInfoDto;
import com.jungeeks.entity.User;
import com.jungeeks.accounts.service.dto.FamilyMemberService;
import com.jungeeks.accounts.service.dto.UserInfoService;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private AuthorizationService authorizationService;
    private UserService userService;
    private FamilyMemberService familyMemberService;

    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setFamilyMemberService(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    public UserInfoDto getUserInfoByUserId(Long id) {
        String uid = authorizationService.getUser().getUid();
        User authUser = userService.getUserByUid(uid);
        String authUserFamilyId = authUser.getFamily().getId();
        User user = userService.getUserById(id);
        if (Objects.isNull(user) || !user.getFamily().getId().equals(authUserFamilyId)) {
            log.warn("Invalid id param");
            throw new InvalidRequestException("Invalid id parameter");
        }

        List<FamilyMemberDto> familyMembers = familyMemberService.getFamilyMembers(user.getFamily().getId());
        log.debug("Get userinfo by id");
        return UserInfoDto.builder()
                .username(user.getName())
                .familyMembers(familyMembers)
                .photoPath(user.getPhoto().get(0).getPath())
                .build();
    }

    @Override
    public UserInfoDto getUserInfoByUserUId(String uId) {
        User user = userService.getUserByUid(uId);
        List<FamilyMemberDto> familyMembers = familyMemberService.getFamilyMembers(user.getFamily().getId());
        log.debug("Get userinfo by uId");
        return UserInfoDto.builder()
                .username(user.getName())
                .familyMembers(familyMembers)
                .photoPath(user.getPhoto().get(0).getPath())
                .build();
    }

}