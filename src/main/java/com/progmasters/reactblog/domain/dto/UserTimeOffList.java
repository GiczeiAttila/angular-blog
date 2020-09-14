package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.TimeOffDateRange;
import com.progmasters.reactblog.domain.TimeOffStatusEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class UserTimeOffList {

    private Long userId;
    private String startDate;
    private String endDate;
    private TimeOffStatusEnum status;

    public UserTimeOffList() {
    }

    public UserTimeOffList(TimeOffDateRange timeOff) {
        this.userId = timeOff.getId();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.startDate = dateFormat.format(timeOff.getStartDate());
        this.endDate = dateFormat.format(timeOff.getEndDate());
        this.status = timeOff.getStatus();
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public TimeOffStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TimeOffStatusEnum status) {
        this.status = status;
    }
}
