package com.progmasters.reactblog.domain.dto;

import java.util.List;

public class PostFormInitData {

    private List<CategoryOption> categories;
    private List<TypeOption> types;

    public PostFormInitData(List<CategoryOption> category, List<TypeOption> type) {
        this.categories = category;
        this.types = type;
    }

    public List<CategoryOption> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryOption> categories) {
        this.categories = categories;
    }

    public List<TypeOption> getTypes() {
        return types;
    }

    public void setTypes(List<TypeOption> types) {
        this.types = types;
    }
}
