package com.jungeeks.service.dto;

import com.jungeeks.dto.FamilyMemberDto;

import java.util.List;

/**
 * The interface Family member service.
 */
public interface FamilyMemberService {
    /**
     * Gets family members.
     *
     * @param familyId the family id
     * @return the family members
     */
    public List<FamilyMemberDto> getFamilyMembers(String familyId);
}
