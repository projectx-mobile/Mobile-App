package com.jungeeks.entitiy.repositories;

import com.jungeeks.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}