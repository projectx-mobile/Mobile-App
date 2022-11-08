package com.jungeeks.service.entity;

import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.FamilyTask;

import java.util.List;

public interface FamilyTaskService {

    FamilyTask save(FamilyTask familyTask);

    FamilyTask findById(Long taskId);
    FamilyTask findByFamilyId(String familyId);
}
