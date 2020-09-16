package com.progmasters.reactblog.domain.dto;

public class MeetingRoomFormData {

    private String name;
    private Integer seats;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeats() {
        return seats;
    }

    public void setSeats(Integer seats) {
        this.seats = seats;
    }
}
