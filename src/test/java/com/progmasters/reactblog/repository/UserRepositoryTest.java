package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        User userActive = UserFactory.getUser(UserStatusEnum.ACTIVE);
        userRepository.save(userActive);
        User userArchive = UserFactory.getUser(UserStatusEnum.ARCHIVE);
        userRepository.save(userArchive);
        User userBlocked = UserFactory.getUser(UserStatusEnum.BLOCKED);
        userRepository.save(userBlocked);
        User userRegistered = UserFactory.getUser(UserStatusEnum.REGISTERED);
        userRepository.save(userRegistered);
        User userReset = UserFactory.getUser(UserStatusEnum.RESET);
        userRepository.save(userReset);
    }

    @ParameterizedTest
    @EnumSource(UserStatusEnum.class)
    void findAllByStatus() {
        List<User> userList = userRepository.findAllByStatus(UserStatusEnum.ACTIVE);
        assertEquals(UserStatusEnum.ACTIVE, userList.get(0).getUserStatus());
    }

    private static class UserFactory{
        private static Long id = 1L;
        public static User getUser(UserStatusEnum userStatusEnum){
            User user = new User();
            user.setUserStatus(userStatusEnum);
            user.setId(id++);
            return user;
        }
    }
}
