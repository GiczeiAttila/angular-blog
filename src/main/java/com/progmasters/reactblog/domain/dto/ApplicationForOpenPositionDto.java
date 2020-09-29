package com.progmasters.reactblog.domain.dto;

public class ApplicationForOpenPositionDto {

    private Long applicantId;
    private Long openPositionId;

    public ApplicationForOpenPositionDto() {
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
    }

    public Long getOpenPositionId() {
        return openPositionId;
    }

    public void setOpenPositionId(Long openPositionId) {
        this.openPositionId = openPositionId;
    }

}
