package com.jungeeks.repository;

import com.jungeeks.entitiy.Reward;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RewardRepository extends JpaRepository<Reward, Long> {
}