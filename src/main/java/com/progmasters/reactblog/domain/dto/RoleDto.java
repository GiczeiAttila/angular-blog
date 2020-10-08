package com.progmasters.reactblog.domain.dto;


import com.progmasters.reactblog.domain.Role;

public class RoleDto {
    private String name;
    private String displayName;

    public RoleDto(Role role) {
        this.name = role.toString();
        this.displayName = role.getDisplayName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
