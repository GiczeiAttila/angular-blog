package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.MeetingStatus;

public class MeetingStatusChangeDto {

    private Long meetingId;
    private MeetingStatus status;


    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public MeetingStatus getStatus() {
        return status;
    }

    public void setStatus(MeetingStatus status) {
        this.status = status;
    }
}
