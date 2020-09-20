package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.TimeOffDateRange;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class CalendarAcceptedTimeOffListDto {

    private String color;
    private String title;
    private String start;
    private String end;

    public CalendarAcceptedTimeOffListDto() {
    }

    public CalendarAcceptedTimeOffListDto(TimeOffDateRange timeOff) {
        this.title = "Time off - Accepted";
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.start = dateFormat.format(timeOff.getStartDate());
        this.end = dateFormat.format(timeOff.getEndDate());
        this.color = "#45D09E";
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
