package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.MeetingRoom;
import com.progmasters.reactblog.domain.dto.MeetingRoomFormData;
import com.progmasters.reactblog.repository.MeetingRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeetingService {

    private MeetingRoomRepository meetingRoomRepository;

    @Autowired
    public MeetingService(MeetingRoomRepository meetingRoomRepository) {
        this.meetingRoomRepository = meetingRoomRepository;
    }


    public MeetingRoom saveMeetingRoom(MeetingRoomFormData meetingRoomFormData) {
        MeetingRoom meetingRoom = this.meetingRoomRepository.save(new MeetingRoom(meetingRoomFormData));
        return meetingRoom;
    }
}
