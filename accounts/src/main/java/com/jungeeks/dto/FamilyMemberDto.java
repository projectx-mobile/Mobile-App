package com.jungeeks.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class FamilyMemberDto {
    private Long id;
    private String username;
    private String photoPath;
}
