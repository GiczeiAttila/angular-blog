package com.progmasters.reactblog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingReservationUpdatedForm {

    private final List<Long> participantsId = new ArrayList<>();
    private Long meetingId;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;
    private Long creatorId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateTime;
    private Long meetingRoomId;


    public Long getMeetingId() {
        return meetingId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<Long> getParticipantsId() {
        return participantsId;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Long getMeetingRoomId() {
        return meetingRoomId;
    }
}
