package com.progmasters.reactblog.integration;

import com.progmasters.reactblog.domain.OpenPosition;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.OpenPositionFormDto;
import com.progmasters.reactblog.domain.dto.OpenPositionListItemDto;
import com.progmasters.reactblog.service.OpenPositionService;
import com.progmasters.reactblog.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Stream;

@SpringBootTest
@Transactional
@Rollback
public class OpenPositionIT {
    @Autowired
    private OpenPositionService openPositionService;
    @Autowired
    private UserService userService;

    @ParameterizedTest(name = "{index} => plusDay1={0}, plusDay2={1}, plusDay3={2}, plusDay4={3}, expectedNumber={4}")
    //CsvSource helyileg megadva
//    @CsvSource({
//            "1,2,3,4,3",
//            "0,1,2,3,2",
//            "-1,0,1,2,2",
//            "-2,-1,0,1,1",
//            "0,1,0,0,0"
//    })

    //CsvFileSource fileban megadva
//    @CsvFileSource(resources = "/open-position-test.csv")

    @MethodSource("parameterCreator")
    void testGetActiveOpenPositions(int plusDay1, int plusDay2, int plusDay3, int plusDay4, int expectedNumber) {
        OpenPositionFormDto openPositionFormDto1 = OpenPositionFormDtoFactory.getOpenPositionFormDto(plusDay1);
        OpenPositionFormDto openPositionFormDto2 = OpenPositionFormDtoFactory.getOpenPositionFormDto(plusDay2);
        OpenPositionFormDto openPositionFormDto3 = OpenPositionFormDtoFactory.getOpenPositionFormDto(plusDay3);
        OpenPositionFormDto openPositionFormDto4 = OpenPositionFormDtoFactory.getOpenPositionFormDto(plusDay4);
        User user1 = UserFactory.getUser(openPositionFormDto1.getUserId());
        User user2 = UserFactory.getUser(openPositionFormDto2.getUserId());
        User user3 = UserFactory.getUser(openPositionFormDto3.getUserId());
        User user4 = UserFactory.getUser(openPositionFormDto4.getUserId());
        userService.saveUser(user1);
        userService.saveUser(user2);
        userService.saveUser(user3);
        userService.saveUser(user4);
        int offset = openPositionService.getActiveOpenPositions(user2.getId()).size();
        expectedNumber = expectedNumber + offset;
        OpenPosition openPosition1 = openPositionService.saveOpenPosition(openPositionFormDto1);
        OpenPosition openPosition2 = openPositionService.saveOpenPosition(openPositionFormDto2);
        OpenPosition openPosition3 = openPositionService.saveOpenPosition(openPositionFormDto3);
        OpenPosition openPosition4 = openPositionService.saveOpenPosition(openPositionFormDto4);

        List<OpenPositionListItemDto> openPositionListItemDtos = openPositionService.getActiveOpenPositions(user2.getId());

        Assertions.assertEquals(expectedNumber, openPositionListItemDtos.size());

    }


    private static class OpenPositionFormDtoFactory {
        private static Long userId = Long.MAX_VALUE;

        public static OpenPositionFormDto getOpenPositionFormDto(int plusDay) {
            OpenPositionFormDto openPositionFormDto = new OpenPositionFormDto();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            format.setTimeZone(TimeZone.getTimeZone("UTC"));
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, plusDay);
            String deadline = format.format(calendar.getTime());
            openPositionFormDto.setDeadline(deadline);
            openPositionFormDto.setPositionName("Test open position number: " + userId);
            openPositionFormDto.setDescription("Test open position description number: " + userId);
            openPositionFormDto.setUserId(userId--);
            System.out.println("\n" + deadline + "\n");
            return openPositionFormDto;
        }
    }

    private static class UserFactory {
        public static User getUser(Long userId) {
            User user = new User();
            user.setId(userId);
            return user;
        }
    }

    private static Stream<Arguments> parameterCreator(){
        return Stream.of(
                Arguments.of(1,2,3,4,3),
                Arguments.of(0,1,2,3,2),
                Arguments.of(-1,0,1,2,2),
                Arguments.of(-2,-1,0,1,1),
                Arguments.of(0,1,0,0,0)
        );
    }
}
