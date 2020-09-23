package com.progmasters.reactblog.domain;

public enum SuggestionStatusEnum {
    ACTIVE("Active"),
    ACCEPTED("Accepted"),
    IMPLEMENTED("Implemented"),
    REJECTED("Rejected");

    private final String displayName;

    SuggestionStatusEnum(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
