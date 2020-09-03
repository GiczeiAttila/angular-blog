package com.progmasters.reactblog.repository;


import com.progmasters.reactblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.userStatus = \"ACTIVE\"")
    List<User> findAllByWithActiveStatus();
}
