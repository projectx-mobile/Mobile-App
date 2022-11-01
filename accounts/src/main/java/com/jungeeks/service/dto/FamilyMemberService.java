package com.jungeeks.service.dto;

import com.jungeeks.dto.FamilyMemberDto;

import java.util.List;

public interface FamilyMemberService {

    public List<FamilyMemberDto> getFamilyMembers(String familyId);
}
