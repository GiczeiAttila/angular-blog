package com.progmasters.reactblog.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "meetingRoom")
public class MeetingRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "seats")
    private Integer seats;

    @OneToMany(mappedBy = "meetingRoom")
    private List<MeetingReservation> meetingList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<MeetingReservation> getMeetingList() {
        return meetingList;
    }

    public void setMeetingList(List<MeetingReservation> meetingList) {
        this.meetingList = meetingList;
    }
}
