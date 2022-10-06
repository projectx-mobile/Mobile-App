package com.jungeeks.email.repo;

import com.jungeeks.entitiy.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByEmail(String email);

    Optional<User> findById(String id);
}
