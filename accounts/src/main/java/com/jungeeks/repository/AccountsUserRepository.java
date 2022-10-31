package com.jungeeks.repository;

import com.jungeeks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("accounts_userRepository")
public interface AccountsUserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by id optional.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<User> findUserById(Long id);

    /**
     * Find all by family id optional.
     *
     * @param familyId the family id
     * @return the optional
     */
    public Optional<List<User>> findAllByFamilyId(String familyId);

    /**
     * Find by firebase id optional.
     *
     * @param firebaseId the firebase id
     * @return the optional
     */
    Optional<User> findByFirebaseId(String firebaseId);
}
