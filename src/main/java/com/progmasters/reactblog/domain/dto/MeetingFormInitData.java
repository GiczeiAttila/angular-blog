package com.progmasters.reactblog.domain.dto;

import java.util.List;

public class MeetingFormInitData {

    private List<MeetingRoomOptionDto> meetingRooms;
    private List<UserForMeetingOptionDto> users;

    public List<MeetingRoomOptionDto> getMeetingRooms() {
        return meetingRooms;
    }

    public List<UserForMeetingOptionDto> getUsers() {
        return users;
    }

}
