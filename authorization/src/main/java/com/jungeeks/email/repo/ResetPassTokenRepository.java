package com.jungeeks.email.repo;

import com.jungeeks.email.entity.ResetPassToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ResetPassTokenRepository extends JpaRepository<ResetPassToken, Long> {
    Optional<ResetPassToken> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE ResetPassToken r " +
            "SET r.confirmedAt = ?2 " +
            "WHERE r.token = ?1")
    int updateConfirmedAt(String token,
                          LocalDateTime confirmedAt);
    @Transactional
    @Query("SELECT COUNT(r) FROM ResetPassToken r WHERE r.email = ?1 AND r.confirmedAt IS NOT NULL")
    int isConfirmedAt(String email);
}

