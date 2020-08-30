package com.progmasters.reactblog.domain;

public enum UserStatusEnum {
    REGISTERED("Registered"),
    ACTIVE("Active"),
    BLOCKED("Blocked"),
    ARCHIVE("Archive");

    private String displayName;

    UserStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
