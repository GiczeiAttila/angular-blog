package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.utils.DateUtils;

import java.time.LocalDateTime;

public class MeetingListItem {

    private String title;
    private String description;
    private String creatorName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String meetingRoomName;

    public MeetingListItem(MeetingReservation meeting) {
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.creatorName = meeting.getCreator().getFirstName() + " " + meeting.getCreator().getLastName();
        this.startDate = DateUtils.localizeDateTimeFromZonedDateTime(meeting.getStartDate());
        this.endDate = DateUtils.localizeDateTimeFromZonedDateTime(meeting.getEndDate());


        /*DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.startDate = dateFormat.format(meeting.getStartDate());
        this.endDate = dateFormat.format(meeting.getEndDate());
         */
        this.meetingRoomName = meeting.getMeetingRoom().getName();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }
}
