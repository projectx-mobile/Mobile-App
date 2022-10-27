package com.jungeeks.accounts.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamilyMemberDto {
    private Long id;
    private String username;
    private String photoPath;
}
