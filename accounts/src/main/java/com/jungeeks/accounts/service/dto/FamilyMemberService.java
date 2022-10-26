package com.jungeeks.accounts.service.dto;

import com.jungeeks.accounts.dto.FamilyMemberDto;

import java.util.List;

public interface FamilyMemberService {
    public List<FamilyMemberDto> getFamilyMembers(String familyId);
}
