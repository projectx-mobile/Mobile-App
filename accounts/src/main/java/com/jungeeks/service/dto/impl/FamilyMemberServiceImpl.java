package com.jungeeks.service.dto.impl;

import com.jungeeks.entity.Family;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.dto.FamilyMemberDto;
import com.jungeeks.entity.User;
import com.jungeeks.service.dto.FamilyMemberService;
import com.jungeeks.security.service.AuthorizationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("accounts-familyMemberServiceImpl")
public class FamilyMemberServiceImpl implements FamilyMemberService {

    private final UserService userService;
    private final AuthorizationService authorizationService;

    @Autowired
    public FamilyMemberServiceImpl(@Qualifier("accounts-userServiceImpl") UserService userService,
                                   AuthorizationService authorizationService) {
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @Override
    public List<FamilyMemberDto> getFamilyMembers(String familyId) {
        List<User> familyMembers = userService.getAllByFamilyId(familyId);
        String currUserUid = getUid();

        log.debug(String.format("Found %s users by familyId: " + familyId, familyMembers.size()));

        return familyMembers.stream()
                .filter(x -> !x.getFirebaseId().equals(currUserUid))
                .filter(x -> x.getUser_status() != USER_STATUS.REMOVED && x.getUser_status() != USER_STATUS.BANNED)
                .map((x) -> (
                        FamilyMemberDto.builder()
                                .id(x.getId())
                                .userStatus(x.getUser_status())
                                .user_role(x.getUser_role())
                                .username(x.getName())
                                .photoPath(x.getPhoto().get(0).getPath())
                                .build())
                )
                .toList();
    }


    private String getUid() {
        return authorizationService.getUser().getUid();
    }
}
