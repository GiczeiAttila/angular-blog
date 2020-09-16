package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.TimeOffDateRange;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CalendarTimeOffListDto {

    private String title;
    private String startDate;
    private String endDate;

    public CalendarTimeOffListDto() {
    }

    public CalendarTimeOffListDto(TimeOffDateRange timeOff) {
        this.title = "Time off";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDate = dateFormat.format(timeOff.getStartDate());
        this.endDate = dateFormat.format(timeOff.getEndDate());
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
