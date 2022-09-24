package com.jungeeks.repository;

import com.jungeeks.entitiy.User;
import com.jungeeks.entitiy.enums.USER_ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);

    Optional<List<User>> findAllByFamilyId(String familyId);

    @Query(value = "SELECT u FROM User u WHERE u.family.id = ?1 and u.user_role = ?2")
    Optional<List<User>> findAllByFamilyIdAndUser_role(@Param("family_id") String familyId, @Param("user_role") USER_ROLE user_role);

}
