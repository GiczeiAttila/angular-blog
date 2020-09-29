package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.MeetingParticipant;
import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.domain.MeetingStatus;
import com.progmasters.reactblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingParticipantRepository extends JpaRepository<MeetingParticipant, Long> {

    @Query("SELECT m FROM MeetingParticipant m WHERE m.user = :id AND m.meetingReservation.meetingStatus = :status")
    List<MeetingParticipant> findMeetingByUserId(@Param("id") User id, @Param("status") MeetingStatus status);

    @Query("SELECT m FROM MeetingParticipant m WHERE m.meetingReservation = :id")
    List<MeetingParticipant> findUserByMeetingId(@Param("id") MeetingReservation id);

    @Query("SELECT u FROM MeetingParticipant m JOIN m.user u WHERE m.meetingReservation.id = :id AND u.userStatus = 'ACTIVE' OR" +
            " u.userStatus = 'RESET' OR u.userStatus = 'BLOCKED'")
    List<User> findAllUserByMeetingId(@Param("id") Long id);
}
