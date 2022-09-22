package com.jungeeks.repository;

import com.jungeeks.entitiy.RewardRequest;
import com.jungeeks.entitiy.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<RewardRequest, Reward> {
}