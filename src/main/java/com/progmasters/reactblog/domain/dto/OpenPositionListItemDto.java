package com.progmasters.reactblog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progmasters.reactblog.domain.OpenPosition;

import java.util.Date;

public class OpenPositionListItemDto {
    private Long id;
    private String positionName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    private Long userId;

    public OpenPositionListItemDto() {
    }

    public OpenPositionListItemDto(OpenPosition openPosition) {
        this.id = openPosition.getId();
        this.positionName = openPosition.getPositionName();
        this.description = openPosition.getDescription();
        this.deadline = openPosition.getDeadline();
        this.userId = openPosition.getUser().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
