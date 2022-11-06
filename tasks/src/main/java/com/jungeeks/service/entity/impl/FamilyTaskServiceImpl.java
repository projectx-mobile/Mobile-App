package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.FamilyTask;
import com.jungeeks.repository.FamilyTaskRepository;
import com.jungeeks.service.entity.FamilyTaskService;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FamilyTaskServiceImpl implements FamilyTaskService {

    private final FamilyTaskRepository familyTaskRepository;

    @Autowired
    public FamilyTaskServiceImpl(FamilyTaskRepository familyTaskRepository) {
        this.familyTaskRepository = familyTaskRepository;
    }

    @Override
    public FamilyTask save(FamilyTask familyTask) {
        return familyTaskRepository.save(familyTask);
    }
}
