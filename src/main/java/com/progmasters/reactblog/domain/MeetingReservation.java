package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.MeetingReservationFormData;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity
@Table(name = "meetingReservation")
public class MeetingReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "meetingReservation")
    private List<MeetingParticipant> participants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

    public MeetingReservation() {
    }

    public MeetingReservation(MeetingReservationFormData meetingReservationFormData, User creator, MeetingRoom meetingRoom) {
        this.title = meetingReservationFormData.getTitle();
        this.description = meetingReservationFormData.getDescription();
        String endDateDay = meetingReservationFormData.getStartDate().substring(0, 12);
        String endDateWithTime = endDateDay + meetingReservationFormData.getEndDate().substring(12);
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.startDate = format.parse(meetingReservationFormData.getStartDate());
            this.endDate = format.parse(endDateWithTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.creator = creator;
        this.meetingRoom = meetingRoom;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<MeetingParticipant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<MeetingParticipant> participants) {
        this.participants = participants;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public void setMeetingRoom(MeetingRoom meetingRoom) {
        this.meetingRoom = meetingRoom;
    }


}
