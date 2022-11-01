package com.jungeeks.service.dto.impl;

import com.jungeeks.service.entity.UserService;
import com.jungeeks.dto.FamilyMemberDto;
import com.jungeeks.entity.User;
import com.jungeeks.service.dto.FamilyMemberService;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Family member service.
 */
@Slf4j
@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    private UserService userService;
    private AuthorizationService authorizationService;

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
     * Sets authorization service.
     *
     * @param authorizationService the authorization service
     */
    @Autowired
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    /**
     * Gets family members.
     *
     * @param familyId the family id
     * @return the family members
     */
    @Override
    public List<FamilyMemberDto> getFamilyMembers(String familyId) {
        List<User> familyMembers = userService.getAllByFamilyId(familyId);
        String currUserUid = authorizationService.getUser().getUid();
        log.debug(String.format("Found %s users by familyId: " + familyId, familyMembers.size()));
        return familyMembers.stream()
                .filter(x -> !x.getFirebaseId().equals(currUserUid))
                .map((x) -> (
                        FamilyMemberDto.builder()
                                .id(x.getId())
                                .username(x.getName())
                                .photoPath(x.getPhoto().get(0).getPath())
                                .build())
                )
                .toList();
    }
}
