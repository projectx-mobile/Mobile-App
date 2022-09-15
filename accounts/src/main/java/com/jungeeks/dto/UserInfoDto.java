package com.jungeeks.dto;

import com.jungeeks.dto.enums.SIGN_UP_TYPE;
import com.jungeeks.entitiy.enums.USER_STATUS;
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
    private String email;
    private Long passwordLength;
    private USER_STATUS userStatus;
    private SIGN_UP_TYPE signUpType;
    private List<FamilyMemberDto> familyMembers;
    private String photoFileName;
}
