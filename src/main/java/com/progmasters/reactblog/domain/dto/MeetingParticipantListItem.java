package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.MeetingParticipant;

public class MeetingParticipantListItem {

    private String userName;
    private Long userId;

    public MeetingParticipantListItem(MeetingParticipant participant) {
        this.userName = participant.getUser().getFirstName() + " " + participant.getUser().getLastName();
        this.userId = participant.getUser().getId();
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
