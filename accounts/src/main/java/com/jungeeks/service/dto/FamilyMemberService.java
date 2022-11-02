package com.jungeeks.service.dto;

import com.jungeeks.dto.FamilyMemberDto;
import com.jungeeks.entity.Family;

import java.util.List;

public interface FamilyMemberService {

    List<FamilyMemberDto> getFamilyMembers(String familyId);
}
