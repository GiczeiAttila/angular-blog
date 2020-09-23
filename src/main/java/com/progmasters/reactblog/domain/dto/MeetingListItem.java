package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.MeetingReservation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class MeetingListItem {

    private String title;
    private String description;
    private String creatorName;
    private String startDate;
    private String endDate;
    private String meetingRoomName;

    public MeetingListItem(MeetingReservation meeting) {
        this.title = meeting.getTitle();
        this.description = meeting.getDescription();
        this.creatorName = meeting.getCreator().getFirstName() + " " + meeting.getCreator().getLastName();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.startDate = dateFormat.format(meeting.getStartDate());
        this.endDate = dateFormat.format(meeting.getEndDate());
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName;
    }
}
