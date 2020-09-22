package com.progmasters.reactblog.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class MeetingReservationFormData {

    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private Long creatorId;
    private List<Long> participantsId = new ArrayList<>();
    private Long meetingRoomId;


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public List<Long> getParticipantsId() {
        return participantsId;
    }

    public Long getMeetingRoomId() {
        return meetingRoomId;
    }
}
