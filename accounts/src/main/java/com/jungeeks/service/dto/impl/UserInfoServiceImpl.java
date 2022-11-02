package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.FamilyIdDto;
import com.jungeeks.exception.InvalidRequestException;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.dto.FamilyMemberDto;
import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.entity.User;
import com.jungeeks.service.dto.FamilyMemberService;
import com.jungeeks.service.dto.UserInfoService;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    @Override
    public UserInfoDto getUserInfoByUserUId(String uId) {
        User user = userService.getUserByUid(uId);
        List<FamilyMemberDto> familyMembers = familyMemberService.getFamilyMembers(user.getFamily().getId());
        log.debug("Get userinfo by uId");
        return UserInfoDto.builder()
                .username(user.getName())
                .user_role(user.getUser_role())
                .familyMembers(familyMembers)
                .photoPath(user.getPhoto().get(0).getPath())
                .build();
    }

    @Override
    public FamilyIdDto getFamilyId() {
        String uid = authorizationService.getUser().getUid();
        String id = userService.getUserByUid(uid).getFamily().getId();
        return FamilyIdDto.builder()
                .id(id)
                .build();
    }

    @Override
    public UserInfoDto getCurrentUserInfo() {
        String uid = authorizationService.getUser().getUid();
        return getUserInfoByUserUId(uid);
    }
}