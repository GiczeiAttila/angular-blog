package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.MeetingRoom;

public class MeetingRoomOptionDto {

    private final Long roomId;
    private final String name;
    private final Integer seats;

    public MeetingRoomOptionDto(MeetingRoom room) {
        this.name = room.getName();
        this.seats = room.getSeats();
        this.roomId = room.getId();
    }

    public Long getRoomId() {
        return roomId;
    }

    public String getName() {
        return name;
    }

    public Integer getSeats() {
        return seats;
    }

}
