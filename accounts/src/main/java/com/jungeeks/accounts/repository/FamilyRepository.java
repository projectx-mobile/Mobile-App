package com.jungeeks.accounts.repository;

import com.jungeeks.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family,String> {
}
