package com.jungeeks.repository;

import com.jungeeks.entitiy.SocialCredentials;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialCredentialsRepository extends JpaRepository<SocialCredentials, Long> {
}