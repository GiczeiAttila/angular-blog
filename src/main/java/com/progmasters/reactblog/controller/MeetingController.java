package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.domain.MeetingRoom;
import com.progmasters.reactblog.domain.dto.MeetingReservationFormData;
import com.progmasters.reactblog.domain.dto.MeetingRoomFormData;
import com.progmasters.reactblog.domain.dto.MeetingRoomOptionDto;
import com.progmasters.reactblog.service.MeetingService;
import com.progmasters.reactblog.validator.MeetingRoomFormDataValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private MeetingService meetingService;
    private MeetingRoomFormDataValidator meetingRoomFormDataValidator;

    private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public MeetingController(MeetingService meetingService, MeetingRoomFormDataValidator meetingRoomFormDataValidator) {
        this.meetingService = meetingService;
        this.meetingRoomFormDataValidator = meetingRoomFormDataValidator;
    }

    @InitBinder("meetingRoomFormData")
    public void initMeetingRoomFormValidator(WebDataBinder binder) {
        binder.addValidators(meetingRoomFormDataValidator);
    }

    @PostMapping("createRoom")
    public ResponseEntity<Void> createMeetingRoom(@Valid @RequestBody MeetingRoomFormData meetingRoomFormData) {
        MeetingRoom meetingRoom = this.meetingService.saveMeetingRoom(meetingRoomFormData);
        if (meetingRoom != null) {
            logger.info("Create new meeting room");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("addMeeting")
    public ResponseEntity<Void> addMeetingReservation(@RequestBody MeetingReservationFormData meetingReservationFormData) {
        MeetingReservation meetingReservation = this.meetingService.addMeetingReservation(meetingReservationFormData);
        if (meetingReservation != null) {
            logger.info("Add new meeting");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("meetingRoom")
    public ResponseEntity<List<MeetingRoomOptionDto>> getAllMeetingRooms() {
        List<MeetingRoomOptionDto> meetingRooms = this.meetingService.getMeetingRooms();
        if (meetingRooms != null) {
            logger.info("Request all meeting room");
            return new ResponseEntity<>(meetingRooms, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
