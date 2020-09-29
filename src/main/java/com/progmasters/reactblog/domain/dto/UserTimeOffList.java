package com.progmasters.reactblog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progmasters.reactblog.domain.TimeOffDateRange;
import com.progmasters.reactblog.domain.TimeOffStatusEnum;

import java.time.LocalDate;

import static com.progmasters.reactblog.utils.DateUtils.localizeDateFromZonedDateTime;

public class UserTimeOffList {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private TimeOffStatusEnum status;

    public UserTimeOffList() {
    }

    public UserTimeOffList(TimeOffDateRange timeOff) {
        this.startDate = localizeDateFromZonedDateTime(timeOff.getStartDate());
        this.endDate = localizeDateFromZonedDateTime(timeOff.getEndDate());
        this.status = timeOff.getStatus();
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
