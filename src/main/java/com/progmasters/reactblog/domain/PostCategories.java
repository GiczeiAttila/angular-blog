package com.progmasters.reactblog.domain;

public enum PostCategories {

    CORPORATE("Corporate news"),
    FREE_TIME_ACTIVITIES("Free time activities"),
    PROFESSIONAL("Professional news");

    private final String displayName;

    PostCategories(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
