package com.jungeeks.repository;

import com.jungeeks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserById(Long id);
    public Optional<List<User>> findAllByFamilyId( String familyId);
}
