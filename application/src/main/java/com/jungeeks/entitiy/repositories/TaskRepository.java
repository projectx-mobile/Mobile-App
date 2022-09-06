package com.jungeeks.entitiy.repositories;

import com.jungeeks.entitiy.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}