package com.LPC.user_service.repository;

import com.LPC.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository //data access
public interface UserRepository extends JpaRepository<User, Long> {

}
