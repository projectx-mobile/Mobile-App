package com.jungeeks.repository;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import com.jungeeks.entity.enums.USER_STATUS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountsUserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);

    Optional<List<User>> findAllByFamilyId(String familyId);

    Optional<User> findByFirebaseId(String firebaseId);

    @Query(value = "SELECT u FROM User u WHERE u.family.id = ?1 and u.user_role = ?2 and u.user_status = ?3")
    Optional<List<User>> findAllByFamilyIdAndUser_role(@Param("family_id") String familyId,
                                                       @Param("user_role") USER_ROLE user_role,
                                                       @Param("user_status") USER_STATUS user_status);

}
