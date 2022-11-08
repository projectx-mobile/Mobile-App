package com.jungeeks.repository;

import com.jungeeks.dto.TaskDto;
import com.jungeeks.entity.FamilyTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("family_taskRepository")
public interface FamilyTaskRepository extends JpaRepository<FamilyTask, Long> {

    Optional<List<TaskDto>> findAllByFamilyId(String familyId);
}
