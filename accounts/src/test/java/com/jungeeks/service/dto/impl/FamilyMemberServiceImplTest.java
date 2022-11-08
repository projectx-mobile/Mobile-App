package com.jungeeks.service.dto.impl;

import com.jungeeks.dto.FamilyMemberDto;
import com.jungeeks.entity.Photo;
import com.jungeeks.entity.User;
import com.jungeeks.service.entity.UserService;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Tag("unit")
@SpringBootTest(classes = FamilyMemberServiceImplTest.class)
class FamilyMemberServiceImplTest {

    @Mock
    private AuthorizationService authorizationService;

    @Mock
    private UserService userService;

    @InjectMocks
    private FamilyMemberServiceImpl familyMemberService;

    private static SecurityUserFirebase securityUserFirebase;
    private static User testDataValidAuthUser;

    private static final String PHOTO_PATH1 = "path1";
    private static final String PHOTO_PATH2 = "path2";
    private static final String PHOTO_PATH3 = "path3";

    private static List<User> testDataFamilyMembers;
    private static List<FamilyMemberDto> testDataFamilyMemberDtoList;

    @BeforeAll
    static void prepare() {
        securityUserFirebase = SecurityUserFirebase.builder()
                .uid("uid")
                .build();

        testDataValidAuthUser = User.builder()
                .id(1L)
                .name("authUser")
                .firebaseId(securityUserFirebase.getUid())
                .photo(List.of(Photo.builder()
                        .path(PHOTO_PATH1)
                        .build()))
                .build();

        User testDataFamilyMember1 = User.builder()
                .id(1L)
                .name("authUser")
                .firebaseId("uid1")
                .photo(List.of(Photo.builder()
                        .path(PHOTO_PATH2)
                        .build()))
                .build();

        User testDataFamilyMember2 = User.builder()
                .id(1L)
                .name("authUser")
                .firebaseId("uid2")
                .photo(List.of(Photo.builder()
                        .path(PHOTO_PATH3)
                        .build()))
                .build();

        testDataFamilyMembers = List.of(testDataValidAuthUser,
                testDataFamilyMember1,
                testDataFamilyMember2);

        testDataFamilyMemberDtoList = testDataFamilyMembers.stream()
                .filter(x -> !x.getFirebaseId().equals(testDataValidAuthUser.getFirebaseId()))
                .map((x) -> (
                        FamilyMemberDto.builder()
                                .id(x.getId())
                                .username(x.getName())
                                .photoPath(x.getPhoto().get(0).getPath())
                                .build())
                )
                .toList();

    }

    @Test
    void getFamilyMembers() {
        when(authorizationService.getUser()).thenReturn(securityUserFirebase);
        when(userService.getAllByFamilyId(any())).thenReturn(testDataFamilyMembers);

        List<FamilyMemberDto> result = familyMemberService.getFamilyMembers("familyId");

        assertEquals(testDataFamilyMemberDtoList, result);
    }
}