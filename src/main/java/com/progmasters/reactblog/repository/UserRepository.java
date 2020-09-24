package com.progmasters.reactblog.repository;


import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.userStatus = :status")
    List<User> findAllByStatus(@Param("status") UserStatusEnum status);

    @Query("SELECT u FROM User u WHERE u.userStatus = :status AND u.id <> :id")
    List<User> findAllActiveUser(@Param("status") UserStatusEnum status, @Param("id") Long id);


}
