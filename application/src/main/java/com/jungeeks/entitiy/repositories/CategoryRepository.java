package com.jungeeks.entitiy.repositories;

import com.jungeeks.entitiy.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>{
}