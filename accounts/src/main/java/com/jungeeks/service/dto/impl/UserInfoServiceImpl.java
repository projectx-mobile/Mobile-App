package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.FamilyIdDto;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.exception.enums.ERROR_CODE;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.dto.FamilyMemberDto;
import com.jungeeks.dto.UserInfoDto;
import com.jungeeks.entity.User;
import com.jungeeks.service.dto.FamilyMemberService;
import com.jungeeks.service.dto.UserInfoService;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
@Slf4j
@Service("accounts-userInfoServiceImpl")
public class UserInfoServiceImpl implements UserInfoService {

    private AuthorizationService authorizationService;
    private UserService userService;
    private FamilyMemberService familyMemberService;

    public UserInfoServiceImpl(AuthorizationService authorizationService,
                               @Qualifier("accounts-userServiceImpl") UserService userService,
                               FamilyMemberService familyMemberService) {
        this.authorizationService = authorizationService;
        this.userService = userService;
        this.familyMemberService = familyMemberService;
    }

    public UserInfoDto getUserInfoByUserId(Long id) {
        User authUser = userService.getUserByUid(getUid());
        String authUserFamilyId = authUser.getFamily().getId();
        User user = userService.getUserById(id);
        if (Objects.isNull(user) || !user.getFamily().getId().equals(authUserFamilyId)) {
            throw new BusinessException("Invalid id parameter", ERROR_CODE.INVALID_REQUEST);
        }

        List<FamilyMemberDto> familyMembers = familyMemberService.getFamilyMembers(user.getFamily().getId());
        log.debug("Get userinfo by id");

        return UserInfoDto.builder()
                .username(user.getName())
                .familyMembers(familyMembers)
                .userStatus(user.getUser_status())
                .user_role(user.getUser_role())
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
                .userStatus(user.getUser_status())
                .familyMembers(familyMembers)
                .photoPath(user.getPhoto().get(0).getPath())
                .build();
    }

    @Override
    public FamilyIdDto getFamilyId() {
        String id = userService.getUserByUid(getUid()).getFamily().getId();
        return FamilyIdDto.builder()
                .id(id)
                .build();
    }

    @Override
    public UserInfoDto getCurrentUserInfo() {
        return getUserInfoByUserUId(getUid());
    }

    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}