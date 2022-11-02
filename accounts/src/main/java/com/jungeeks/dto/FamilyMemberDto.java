package com.jungeeks.dto;

import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FamilyMemberDto {

    private Long id;
    private String username;
    private String photoPath;
    private USER_STATUS userStatus;
    private USER_ROLE user_role;
}
