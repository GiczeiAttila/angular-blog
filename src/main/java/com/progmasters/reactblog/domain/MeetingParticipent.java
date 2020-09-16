package com.progmasters.reactblog.domain;

import javax.persistence.*;

@Entity
@Table(name = "meeting_participent")
public class MeetingParticipent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "meeting_id")
    private MeetingReservation meetingReservation;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MeetingReservation getMeetingReservation() {
        return meetingReservation;
    }

    public void setMeetingReservation(MeetingReservation meetingReservation) {
        this.meetingReservation = meetingReservation;
    }
}
