package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.Family;
import com.jungeeks.entity.FamilyTask;
import com.jungeeks.entity.User;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.repository.FamilyTaskRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = FamilyTaskServiceImplTest.class)
@Tag("unit")
class FamilyTaskServiceImplTest {

    @InjectMocks
    private FamilyTaskServiceImpl familyTaskService;
    @Mock
    private FamilyTaskRepository familyTaskRepository;

    private static FamilyTask familyTask;

    @BeforeAll
    static void setUp() {
        familyTask = FamilyTask.builder()
                .id(1L)
                .family(Family.builder()
                        .id("testFamilyId")
                        .build())
                .author(User.builder()
                        .id(1L)
                        .build())
                .build();
    }

    @Test
    void save() {
        when(familyTaskRepository.save(familyTask)).thenReturn(familyTask);

        FamilyTask familyTask1 = familyTaskService.save(familyTask);
        assertEquals(familyTask1, familyTask);
    }

    @Test
    void findByIdPositive() {
        when(familyTaskRepository.findById(any())).thenReturn(Optional.ofNullable(familyTask));

        FamilyTask familyTask1 = familyTaskService.findById(any());
        assertEquals(familyTask1, familyTask);
    }

    @Test
    void findByIdNegative() {
        when(familyTaskRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(BusinessException.class, () -> familyTaskService.findById(any()));
    }
}