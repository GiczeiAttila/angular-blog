package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.MeetingReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetingReservationRepository extends JpaRepository<MeetingReservation, Long> {
}
