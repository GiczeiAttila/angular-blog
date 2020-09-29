package com.progmasters.reactblog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progmasters.reactblog.domain.MeetingParticipant;
import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.utils.DateUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingReservationData {

    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;
    private Long creatorId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateTime;
    private final List<MeetingParticipantListItem> participantsList = new ArrayList<>();
    private MeetingRoomOptionDto meetingRoom;

    public MeetingReservationData(MeetingReservation meetingReservation, List<MeetingParticipant> participants) {
        this.title = meetingReservation.getTitle();
        this.description = meetingReservation.getDescription();
        for (MeetingParticipant participant : participants) {
            participantsList.add(new MeetingParticipantListItem(participant));
        }
        this.startDateTime = DateUtils.localizeDateTimeFromZonedDateTime(meetingReservation.getStartDate());
        this.endDateTime = DateUtils.localizeDateTimeFromZonedDateTime(meetingReservation.getEndDate());
        this.creatorId = meetingReservation.getCreator().getId();
        this.meetingRoom = new MeetingRoomOptionDto(meetingReservation.getMeetingRoom());
    }


    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<MeetingParticipantListItem> getParticipantsList() {
        return participantsList;
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

    public MeetingRoomOptionDto getMeetingRoom() {
        return meetingRoom;
    }
}
