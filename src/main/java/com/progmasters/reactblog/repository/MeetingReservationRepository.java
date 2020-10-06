package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.domain.MeetingRoom;
import com.progmasters.reactblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface MeetingReservationRepository extends JpaRepository<MeetingReservation, Long> {

    @Query("SELECT m FROM MeetingReservation m WHERE m.creator = :id AND m.startDate >= :now ORDER BY m.startDate DESC")
    List<MeetingReservation> findMeetingByCreatorId(@Param("id") User id, @Param("now") ZonedDateTime dateTimeNow);

    @Query("SELECT m FROM MeetingReservation m WHERE m.meetingRoom = :meetingRoom AND m.meetingStatus = 'ACTIVE'")
    List<MeetingReservation> findAllActiveByRoomId(@Param("meetingRoom") MeetingRoom meetingRoom);
}
