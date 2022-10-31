package com.jungeeks.dto;

import com.jungeeks.entity.enums.USER_ROLE;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfoDto {
    private String username;
    private USER_ROLE user_role;
    private List<FamilyMemberDto> familyMembers;
    private String photoPath;
}
