package com.jungeeks.repository;

import com.jungeeks.entitiy.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
}