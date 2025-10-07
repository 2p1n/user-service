package com.LPC.user_service.repository;

import com.LPC.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //data access
public interface UserRepository extends JpaRepository<User, Long> {

    //SELECT * FROM user WHERE email = ?
    //@Query("SELECT u FROM User u WHERE u.email = ?1") // this can be commented out
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
}
