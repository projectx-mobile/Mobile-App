package com.jungeeks.repository;

import com.jungeeks.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);
    Optional<List<User>> findAllByFamilyId(String id);
}