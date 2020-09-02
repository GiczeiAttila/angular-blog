package com.progmasters.reactblog.domain.dto;

public class SuggestionListItemDto {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private Long countUp;
    private Long countDown;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCountUp() {
        return countUp;
    }

    public void setCountUp(Long countUp) {
        this.countUp = countUp;
    }

    public Long getCountDown() {
        return countDown;
    }

    public void setCountDown(Long countDown) {
        this.countDown = countDown;
    }
}
