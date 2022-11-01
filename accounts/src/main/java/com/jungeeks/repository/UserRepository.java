package com.jungeeks.repository;

import com.jungeeks.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {


    /**
     * @param aLong
     * @return
     */
    Optional<User> findUserById(Long aLong);


//    /**
//     * @param uid
//     * @return
//     */
//    Optional<User> findByUid(String uid);

    Optional<User> findByFirebaseId(String firebaseId);


}