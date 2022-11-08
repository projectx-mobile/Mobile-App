package com.jungeeks.service.entity.impl;

import com.jungeeks.entity.FamilyTask;
import com.jungeeks.exception.BusinessException;
import com.jungeeks.exception.enums.ERROR_CODE;
import com.jungeeks.repository.FamilyTaskRepository;
import com.jungeeks.service.entity.FamilyTaskService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@Slf4j
public class FamilyTaskServiceImpl implements FamilyTaskService {

    private final FamilyTaskRepository familyTaskRepository;

    @Autowired
    public FamilyTaskServiceImpl(FamilyTaskRepository familyTaskRepository) {
        this.familyTaskRepository = familyTaskRepository;
    }

    @Override
    public FamilyTask save(FamilyTask familyTask) {
        log.debug("Request save familyTask with family {} and author id {}", familyTask.getFamily().getId(), familyTask.getAuthor().getId());

        return familyTaskRepository.save(familyTask);
    }

    @Override
    public FamilyTask findById(Long taskId) {
        log.debug("Request findById by taskId {}", taskId);

        return familyTaskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(String.format("Task with id %s not found", taskId),
                                                            ERROR_CODE.TASK_NOT_FOUND, NOT_FOUND));
    }

    @Override
    public FamilyTask findByFamilyId(String familyId) {
        log.debug("Request findById by familyId {}", familyId);

        return familyTaskRepository.findByFamilyId(familyId);
    }
}
