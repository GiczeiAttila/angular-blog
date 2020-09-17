package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.MeetingRoom;
import com.progmasters.reactblog.domain.dto.MeetingRoomFormData;
import com.progmasters.reactblog.service.MeetingService;
import com.progmasters.reactblog.validator.MeetingRoomFormDataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private MeetingService meetingService;
    private MeetingRoomFormDataValidator meetingRoomFormDataValidator;

    @Autowired
    public MeetingController(MeetingService meetingService, MeetingRoomFormDataValidator meetingRoomFormDataValidator) {
        this.meetingService = meetingService;
        this.meetingRoomFormDataValidator = meetingRoomFormDataValidator;
    }

    @InitBinder("meetingRoomFormData")
    public void initMeetingRoomFormValidator(WebDataBinder binder) {
        binder.addValidators(meetingRoomFormDataValidator);
    }

    @PostMapping("/createRoom")
    public ResponseEntity<Void> createMeetingRoom(@RequestBody MeetingRoomFormData meetingRoomFormData) {
        MeetingRoom meetingRoom = this.meetingService.saveMeetingRoom(meetingRoomFormData);
        if (meetingRoom != null) {
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
}
