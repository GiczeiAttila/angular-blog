package com.progmasters.reactblog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class OpenPositionListItemDto {
    private Long id;
    private String positionName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date deadline;
    private Long userId;

}
