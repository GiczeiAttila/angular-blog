package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    @ValueSource(strings = {"REGISTERED", "RESET", "ACTIVE", "BLOCKED", "ARCHIVE"})
    void testFindAllByStatus(String userStatusEnumString) {
        UserStatusEnum userStatusEnum = UserStatusEnum.valueOf(userStatusEnumString);
        List<User> userList = userRepository.findAllByStatus(userStatusEnum);
        assertEquals(userStatusEnum, userList.get(0).getUserStatus());
    }

    @ParameterizedTest
    @ValueSource(strings = {"REGISTERED", "RESET", "ACTIVE", "BLOCKED", "ARCHIVE"})
    void testFindAllByStatusWithStings(UserStatusEnum userStatusEnum) {
        List<User> userList = userRepository.findAllByStatus(userStatusEnum);
        assertEquals(userStatusEnum, userList.get(0).getUserStatus());
    }

    @ParameterizedTest
    @EnumSource(UserStatusEnum.class)
    void testFindAllByStatusWithFoundUserNumber(UserStatusEnum userStatusEnum) {
        List<User> userList = userRepository.findAllByStatus(userStatusEnum);
        assertEquals(1, userList.size());
    }

    @ParameterizedTest
    @EnumSource(UserStatusEnum.class)
    void testFindAllByStatusWithCorrectStatus(UserStatusEnum userStatusEnum) {
        List<User> userList = userRepository.findAllByStatus(userStatusEnum);
        assertEquals(userStatusEnum, userList.get(0).getUserStatus());
    }
















    /* @ParameterizedTest
     @ValueSource(strings = {"REGISTERED","RESET","ACTIVE","BLOCKED","ARCHIVE"})
     void TestFindAllByStatus(@ConvertWith(UserStatusEnumConverter.class) UserStatusEnum userStatusEnum) {
         List<User> userList = userRepository.findAllByStatus(userStatusEnum);
         assertEquals(userStatusEnum, userList.get(0).getUserStatus());
     }


     final class UserStatusEnumConverter implements ArgumentConverter{

         @Override
         public Object convert(Object source, ParameterContext context) throws ArgumentConversionException {
             checkSource(source);

             String sourceString = (String) source;
             return UserStatusEnum.valueOf(sourceString);
         }

         private void checkSource(Object source) {
             if (source == null){
                 throw new ArgumentConversionException("Cannot convert null source object");
             }
             if (!source.getClass().equals(String.class)){
                 throw new ArgumentConversionException("Cannot convert source object because it's not a string");
             }

             String sourceString = (String) source;
             if (sourceString.trim().isEmpty()){
                 throw new ArgumentConversionException("Cannot convert empty string");
             }
         }
     }
 */
    private static class UserFactory {
        private static Long id = 1L;

        public static User getUser(UserStatusEnum userStatusEnum) {
            User user = new User();
            user.setUserStatus(userStatusEnum);
            user.setId(id++);
            return user;
        }
    }
}
