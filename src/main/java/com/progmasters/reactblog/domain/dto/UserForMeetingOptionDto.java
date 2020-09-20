package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.User;

public class UserForMeetingOptionDto {

    private Long userId;
    private String userName;

    public UserForMeetingOptionDto(User user) {
        this.userId = user.getId();
        this.userName = user.getFirstName() + " " + user.getLastName();
    }


    public Long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }
}
