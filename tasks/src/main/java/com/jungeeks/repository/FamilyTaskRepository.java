package com.jungeeks.repository;

import com.jungeeks.entity.FamilyTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface FamilyTaskRepository extends JpaRepository<FamilyTask, Long> {

    Optional<List<FamilyTask>> findAllByDeadlineBetween(LocalDateTime after, LocalDateTime before);
}
