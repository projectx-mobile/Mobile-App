package com.jungeeks.dto;

import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class UserInfoDto {

    private String username;
    private USER_ROLE user_role;
    private USER_STATUS userStatus;
    private List<FamilyMemberDto> familyMembers;
    private String photoPath;
}
