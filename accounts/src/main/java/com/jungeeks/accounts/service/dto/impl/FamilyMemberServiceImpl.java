package com.jungeeks.accounts.service.dto.impl;

import com.jungeeks.accounts.service.entity.UserService;
import com.jungeeks.accounts.dto.FamilyMemberDto;
import com.jungeeks.entity.User;
import com.jungeeks.accounts.service.dto.FamilyMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamilyMemberServiceImpl implements FamilyMemberService {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<FamilyMemberDto> getFamilyMembers(String familyId) {
        List<User> familyMembers = userService.getAllByFamilyId(familyId);
        Long id = 1L;//Fix this after security configuration(Change skip adding current identified user to List<FamilyMember>)
        return familyMembers.stream()
                .filter(x -> !x.getId().equals(id))
                .map((x) -> (
                        FamilyMemberDto.builder()
                                .id(x.getId())
                                .username(x.getName())
//                                .photoPath(x.getPhoto().get(0).getPath())
                                .build())
                )
                .toList();

    }
}
