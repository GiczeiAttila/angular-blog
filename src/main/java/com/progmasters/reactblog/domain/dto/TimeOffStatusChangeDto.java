package com.progmasters.reactblog.domain.dto;

public class TimeOffStatusChangeDto {

    private Long dateId;
    private String status;

    public Long getDateId() {
        return dateId;
    }

    public void setDateId(Long dateId) {
        this.dateId = dateId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
