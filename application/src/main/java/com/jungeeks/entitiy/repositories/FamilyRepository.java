package com.jungeeks.entitiy.repositories;

import com.jungeeks.entitiy.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, String> {
}