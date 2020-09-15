package com.progmasters.reactblog.domain;

public enum TimeOffStatusEnum {

    ACCEPTED("Accepted"),
    REJECTED("Rejected"),
    PENDING("Pending");

    private String displayName;

    TimeOffStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
