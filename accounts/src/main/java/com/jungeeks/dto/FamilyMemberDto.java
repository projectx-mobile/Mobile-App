package com.jungeeks.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamilyMemberDto {

    private Long id;
    private String username;
    private String photoPath;
}
