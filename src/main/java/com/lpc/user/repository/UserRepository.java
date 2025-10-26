package com.lpc.user.repository;

import com.lpc.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository //data access
public interface UserRepository extends JpaRepository<User, Long> {

}
