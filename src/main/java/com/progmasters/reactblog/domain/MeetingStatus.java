package com.progmasters.reactblog.domain;

public enum MeetingStatus {

    ACTIVE("Active"),
    DELETED("Deleted");

    private String displayName;

    MeetingStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
