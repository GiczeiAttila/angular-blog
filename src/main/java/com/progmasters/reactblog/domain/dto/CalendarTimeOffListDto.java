package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.TimeOffDateRange;
import com.progmasters.reactblog.domain.TimeOffStatusEnum;
import com.progmasters.reactblog.utils.DateUtils;

import java.time.LocalDate;

public class CalendarTimeOffListDto {

    private String color;
    private String title;
    private LocalDate start;
    private LocalDate end;

    public CalendarTimeOffListDto() {
    }

    public CalendarTimeOffListDto(TimeOffDateRange timeOff) {
        if (timeOff.getStatus() == TimeOffStatusEnum.ACCEPTED) {
            this.title = "Time off - Accepted";
        } else {
            this.title = "Time off - Pending";
        }
        this.start = DateUtils.localizeDateFromZonedDateTime(timeOff.getStartDate());
        this.end = DateUtils.localizeDateFromZonedDateTime(timeOff.getEndDate());

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate start) {
        this.start = start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
