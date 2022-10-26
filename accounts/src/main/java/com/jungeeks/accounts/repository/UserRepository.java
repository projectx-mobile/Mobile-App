package com.jungeeks.accounts.repository;

import com.jungeeks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("accountsUserRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findUserById(Long id);
    public Optional<List<User>> findAllByFamilyId( String familyId);
}
