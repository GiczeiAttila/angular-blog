package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.TimeOffDateRange;
import com.progmasters.reactblog.domain.TimeOffStatusEnum;
import com.progmasters.reactblog.utils.DateUtils;

import java.time.LocalDate;

public class TimeOffListItem {

    private Long userId;
    private Long dateId;
    private String userName;
    private LocalDate startDate;
    private LocalDate endDate;
    private TimeOffStatusEnum status;

    public TimeOffListItem(TimeOffDateRange timeOff) {
        this.userId = timeOff.getUser().getId();
        this.dateId = timeOff.getId();
        this.userName = timeOff.getUser().getFirstName() + " " + timeOff.getUser().getLastName();
        this.startDate = DateUtils.localizeDateFromZonedDateTime(timeOff.getStartDate());
        this.endDate = DateUtils.localizeDateFromZonedDateTime(timeOff.getEndDate());
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public TimeOffStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TimeOffStatusEnum status) {
        this.status = status;
    }

}
