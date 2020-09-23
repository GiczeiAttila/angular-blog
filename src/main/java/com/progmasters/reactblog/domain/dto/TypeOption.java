package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.PostTypes;

public class TypeOption {

    private String name;
    private String displayName;

    public TypeOption(PostTypes type) {
        this.name = type.toString();
        this.displayName = type.getDisplayName();
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
