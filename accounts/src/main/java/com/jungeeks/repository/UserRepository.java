package com.jungeeks.repository;

import com.jungeeks.entity.User;
import com.jungeeks.entity.enums.USER_ROLE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Crud repository for {@link User}
 *
 * @author TorusTredent on 10.28.2022
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find all by family id optional.
     *
     * @param familyId the family id
     * @return the optional
     */
    Optional<List<User>> findAllByFamilyId(String familyId);

    /**
     * Find all by family id and user role optional.
     *
     * @param familyId  the family id
     * @param user_role the user role
     * @return the optional
     */
    @Query(value = "SELECT u FROM User u WHERE u.family.id = ?1 and u.user_role = ?2")
    Optional<List<User>> findAllByFamilyIdAndUser_role(@Param("family_id") String familyId,
                                                       @Param("user_role") USER_ROLE user_role);

    /**
     * Find by firebase id optional.
     *
     * @param firebaseId the firebase id
     * @return the optional
     */
    Optional<User> findByFirebaseId(String firebaseId);

    /**
     * @param aLong
     * @return
     */
    Optional<User> findUserById(Long aLong);


    /**
     * @param uid
     * @return
     */
    Optional<User> findByUid(String uid);


}