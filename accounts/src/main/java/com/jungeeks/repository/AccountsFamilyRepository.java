package com.jungeeks.repository;

import com.jungeeks.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountsFamilyRepository extends JpaRepository<Family, String> {
}
