package com.progmasters.reactblog.domain.dto;

public class TimeOffFormData {

    private Long userId;
    private String startDate;
    private String endDate;


    public Long getUserId() {
        return userId;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
