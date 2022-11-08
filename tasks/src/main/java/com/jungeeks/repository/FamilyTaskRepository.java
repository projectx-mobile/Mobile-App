package com.jungeeks.repository;

import com.jungeeks.entity.FamilyTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FamilyTaskRepository extends JpaRepository<FamilyTask, Long> {
    FamilyTask findByFamilyId(String familyId);
}
