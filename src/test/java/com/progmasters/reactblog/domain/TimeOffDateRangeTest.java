package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.TimeOffFormData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;

public class TimeOffDateRangeTest {

    @ParameterizedTest
    @CsvSource({
            "2020-09-21,2020-09-21,1",
            "2020-09-21,2020-09-25,5",
            "2020-09-25,2020-09-28,2",
            "2020-09-26,2020-09-27,0"
    })
    void testCreation(LocalDate startDate, LocalDate endDate, int expected) {
        TimeOffFormData timeOffFormData = new TimeOffFormData(1L,
                startDate,
                endDate);

        TimeOffDateRange timeOffDateRange = new TimeOffDateRange(timeOffFormData, null);
        Assertions.assertEquals(expected, timeOffDateRange.getWeekDays());
    }

}
