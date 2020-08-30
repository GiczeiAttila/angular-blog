package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.PostCategories;


public class CategoryOption {

    private String name;
    private String displayName;

    public CategoryOption(PostCategories category) {
        this.name = category.toString();
        this.displayName = category.getDisplayName();
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
