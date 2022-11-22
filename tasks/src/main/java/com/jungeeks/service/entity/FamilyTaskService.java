package com.jungeeks.service.entity;

import com.jungeeks.entity.FamilyTask;

import java.time.LocalDateTime;
import java.util.List;

public interface FamilyTaskService {

    FamilyTask save(FamilyTask familyTask);

    FamilyTask findById(Long taskId);

    List<FamilyTask> findAllByDate(LocalDateTime atStartOfDay, LocalDateTime nextStartOfDay);
}
