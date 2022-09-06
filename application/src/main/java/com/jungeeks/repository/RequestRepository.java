package com.jungeeks.repository;

import com.jungeeks.entitiy.Request;
import com.jungeeks.entitiy.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Reward> {
}