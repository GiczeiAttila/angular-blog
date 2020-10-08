package com.progmasters.reactblog.domain;

public enum Role {
    ROLE_USER("user"),
    ROLE_MANAGER("manager"),
    ROLE_HR("hr"),
    ROLE_ADMIN("admin"),
    ;

    private String displayName;

    Role(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
