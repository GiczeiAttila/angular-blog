package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.MeetingReservationFormData;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.progmasters.reactblog.utils.DateUtils.convertLocalDateTimeToZonedDateTime;

@Entity
@Table(name = "meetingReservation")
public class MeetingReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description",
            columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "end_date")
    private ZonedDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "meetingReservation")
    private List<MeetingParticipant> participants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "meeting_room_id")
    private MeetingRoom meetingRoom;

    @Enumerated(EnumType.STRING)
    @Column(name = "meeting_status")
    private MeetingStatus meetingStatus;

    public MeetingReservation() {
    }

    public MeetingReservation(MeetingReservationFormData meetingReservationFormData, User creator, MeetingRoom meetingRoom) {
        this.title = meetingReservationFormData.getTitle();
        this.description = meetingReservationFormData.getDescription();
        this.startDate = convertLocalDateTimeToZonedDateTime(meetingReservationFormData.getStartDateTime());
        this.endDate = convertLocalDateTimeToZonedDateTime(meetingReservationFormData.getEndDateTime());
        this.creator = creator;
        this.meetingRoom = meetingRoom;
        this.meetingStatus = MeetingStatus.ACTIVE;
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

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
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

    public MeetingStatus getMeetingStatus() {
        return meetingStatus;
    }

    public void setMeetingStatus(MeetingStatus meetingStatus) {
        this.meetingStatus = meetingStatus;
    }
}
