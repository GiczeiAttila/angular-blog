package com.progmasters.reactblog.domain;

public enum MeetingRoomStatus {

    ACTIVE("Active"),
    DELETED("Deleted");

    private String displayName;

    MeetingRoomStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
