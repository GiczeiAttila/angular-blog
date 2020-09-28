package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.MeetingParticipant;
import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.domain.MeetingStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserMeetingReservationListItem {

    private Long meetingId;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
    private Long creatorId;
    private List<MeetingParticipantListItem> participants = new ArrayList<>();
    private Long meetingRoomId;
    private MeetingStatus status;

    public UserMeetingReservationListItem(MeetingReservation meeting, List<MeetingParticipant> participants) {
        this.meetingId = meeting.getId();
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.startDate = dateFormat.format(meeting.getStartDate());
        this.endDate = dateFormat.format(meeting.getEndDate());
        this.creatorId = meeting.getCreator().getId();
        for (MeetingParticipant participant : participants) {
            this.participants.add(new MeetingParticipantListItem(participant));
        }
        this.meetingRoomId = meeting.getMeetingRoom().getId();
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

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public List<MeetingParticipantListItem> getParticipants() {
        return participants;
    }

    public Long getMeetingRoomId() {
        return meetingRoomId;
    }
}
