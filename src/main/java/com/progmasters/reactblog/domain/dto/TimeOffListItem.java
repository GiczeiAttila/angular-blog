package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.TimeOffDateRange;
import com.progmasters.reactblog.domain.TimeOffStatusEnum;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimeOffListItem {

    private Long userId;
    private Long dateId;
    private String userName;
    private String startDate;
    private String endDate;
    private TimeOffStatusEnum status;

    public TimeOffListItem(TimeOffDateRange timeOff) {
        this.userId = timeOff.getUser().getId();
        this.dateId = timeOff.getId();
        this.userName = timeOff.getUser().getFirstName() + " " + timeOff.getUser().getLastName();
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

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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
