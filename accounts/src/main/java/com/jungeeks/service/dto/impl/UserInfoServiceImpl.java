package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.FamilyIdDto;
import com.jungeeks.entity.Family;
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
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * The type User info service.
 */
@Service
@Slf4j
public class UserInfoServiceImpl implements UserInfoService {

    private AuthorizationService authorizationService;
    private UserService userService;
    private FamilyMemberService familyMemberService;

    /**
     * Sets authorization service.
     *
     * @param authorizationService the authorization service
     */
    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    /**
     * Sets user service.
     *
     * @param userService the user service
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sets family member service.
     *
     * @param familyMemberService the family member service
     */
    @Autowired
    public void setFamilyMemberService(FamilyMemberService familyMemberService) {
        this.familyMemberService = familyMemberService;
    }

    /**
     * Gets user info by user id.
     *
     * @param id the id
     * @return the user info by user id
     */
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

    /**
     * Gets user info by user u id.
     *
     * @param uId the u id
     * @return the user info by user u id
     */
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

    /**
     * Gets family id.
     *
     * @return the family id
     */
    @Override
    public FamilyIdDto getFamilyId() {
        String uid = authorizationService.getUser().getUid();
        String id = userService.getUserByUid(uid).getFamily().getId();
        return FamilyIdDto.builder()
                .id(id)
                .build();
    }
}