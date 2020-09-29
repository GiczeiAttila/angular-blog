package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.MeetingParticipant;
import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.domain.MeetingStatus;
import com.progmasters.reactblog.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserMeetingReservationListItem {

    private Long meetingId;
    private String title;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long creatorId;
    private List<MeetingParticipantListItem> participants = new ArrayList<>();
    private Long meetingRoomId;
    private String meetingRoomName;
    private MeetingStatus status;

    public UserMeetingReservationListItem(MeetingReservation meeting, List<MeetingParticipant> participants) {
        this.meetingId = meeting.getId();
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.startDate = DateUtils.localizeDateTimeFromZonedDateTime(meeting.getStartDate());
        this.endDate = DateUtils.localizeDateTimeFromZonedDateTime(meeting.getEndDate());
        this.creatorId = meeting.getCreator().getId();
        for (MeetingParticipant participant : participants) {
            this.participants.add(new MeetingParticipantListItem(participant));
        }
        this.meetingRoomId = meeting.getMeetingRoom().getId();
        this.meetingRoomName = meeting.getMeetingRoom().getName();
        this.status = meeting.getMeetingStatus();
    }


    public Long getMeetingId() {
        return meetingId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public MeetingStatus getStatus() {
        return status;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public List<MeetingParticipantListItem> getParticipants() {
        return participants;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public Long getMeetingRoomId() {
        return meetingRoomId;
    }
}
