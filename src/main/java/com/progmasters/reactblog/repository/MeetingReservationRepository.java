package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingReservationRepository extends JpaRepository<MeetingReservation, Long> {

    @Query("SELECT m FROM MeetingReservation m WHERE m.creator = :id")
    List<MeetingReservation> findMeetingByCreatorId(@Param("id") User id);
}
