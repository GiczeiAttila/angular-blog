package com.progmasters.reactblog.repository;

import com.progmasters.reactblog.domain.MeetingRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingRoomRepository extends JpaRepository<MeetingRoom, Long> {

    @Query("SELECT r FROM MeetingRoom r WHERE r.status = 'ACTIVE'")
    List<MeetingRoom> findAllActive();
}
