package com.progmasters.reactblog.domain;


public enum PostTypes {

    NEWS("News"),
    EVENT("Event");

    private String displayName;

    PostTypes(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
