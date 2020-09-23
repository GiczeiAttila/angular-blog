package com.progmasters.reactblog.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MeetingReservationFormData {

    private String title;
    private String description;
    private final List<Long> participantsId = new ArrayList<>();
    //TODO Itt lehet megadni egyből LocalDateTime-ot / LocalDate-et, annyi,
    // hogy akkor figyelni kell a formátumra mikor küldjük
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;
    private Long creatorId;
    //TODO Same here...
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endDateTime;
    private Long meetingRoomId;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public List<Long> getParticipantsId() {
        return participantsId;
    }

    public Long getMeetingRoomId() {
        return meetingRoomId;
    }

}
