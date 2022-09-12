package com.jungeeks.repository;

import com.jungeeks.entitiy.FamilyTask;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyTaskRepository extends JpaRepository<FamilyTask, Long> {
}