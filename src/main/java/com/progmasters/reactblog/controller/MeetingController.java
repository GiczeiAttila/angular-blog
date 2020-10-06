package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.MeetingReservation;
import com.progmasters.reactblog.domain.MeetingRoom;
import com.progmasters.reactblog.domain.dto.*;
import com.progmasters.reactblog.service.MeetingService;
import com.progmasters.reactblog.validator.MeetingReservationFormDataValidator;
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
    private MeetingReservationFormDataValidator meetingReservationFormDataValidator;

    private static final Logger logger = LoggerFactory.getLogger(MeetingController.class);

    @Autowired
    public MeetingController(MeetingService meetingService, MeetingRoomFormDataValidator meetingRoomFormDataValidator, MeetingReservationFormDataValidator meetingReservationFormDataValidator) {
        this.meetingService = meetingService;
        this.meetingRoomFormDataValidator = meetingRoomFormDataValidator;
        this.meetingReservationFormDataValidator = meetingReservationFormDataValidator;
    }

    @InitBinder("meetingRoomFormData")
    public void initMeetingRoomFormValidator(WebDataBinder binder) {
        binder.addValidators(meetingRoomFormDataValidator);
    }

    @InitBinder("meetingReservationFormData")
    public void initMeetingReservationFormValidator(WebDataBinder binder) {
        binder.addValidators(meetingReservationFormDataValidator);
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
    public ResponseEntity<Void> addMeetingReservation(@Valid @RequestBody MeetingReservationFormData meetingReservationFormData) {
        MeetingReservation meetingReservation = this.meetingService.addMeetingReservation(meetingReservationFormData);
        if (meetingReservation != null) {
            logger.info("Add new meeting");
            return new ResponseEntity<>(HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("meetingRoom")
    public ResponseEntity<List<MeetingRoomOptionDto>> getActiveMeetingRooms() {
        List<MeetingRoomOptionDto> meetingRooms = this.meetingService.getMeetingRooms();
        if (meetingRooms != null) {
            logger.info("Request all meeting room");
            return new ResponseEntity<>(meetingRooms, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<MeetingListItem>> getMyMeetingList(@PathVariable Long id) {
        List<MeetingListItem> meetingList = this.meetingService.getMyMeetingList(id);
        if (meetingList != null) {
            logger.info("Meeting list is required with id: " + id);
            return new ResponseEntity<>(meetingList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/creator/{id}")
    public ResponseEntity<List<UserMeetingReservationListItem>> getUserMeetingReservationList(@PathVariable Long id) {
        List<UserMeetingReservationListItem> meetingReservationList = this.meetingService.getUserMeetingReservation(id);
        if (meetingReservationList != null) {
            logger.info("Meeting reservation list is required with id: " + id);
            return new ResponseEntity<>(meetingReservationList, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/creator")
    public ResponseEntity<MeetingStatusChangeDto> changeMeetingStatus(@RequestBody MeetingStatusChangeDto statusChangeDto) {
        this.meetingService.changeMeetingStatus(statusChangeDto);
        logger.info("Request to change meeting status to: " +
                statusChangeDto.getStatus() + " with meeting id: " +
                statusChangeDto.getMeetingId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/update/{id}")
    public ResponseEntity<MeetingReservationData> getMeetingById(@PathVariable Long id) {
        MeetingReservationData meeting = this.meetingService.getMeetingDataById(id);
        if (meeting != null) {
            logger.info("Request meeting with id: " + id);
            return new ResponseEntity<>(meeting, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity<MeetingReservation> updateMeeting(@RequestBody MeetingReservationUpdatedForm updatedForm) {
        this.meetingService.updateMeeting(updatedForm);
        logger.info("Request to update meeting with id: " + updatedForm.getMeetingId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("delete-meeting-room")
    public ResponseEntity<Void> deleteMeetingRoom(@RequestBody Long id) {
        this.meetingService.deleteMeetingRoom(id);
        logger.info("Change meeting room status to deleted with id: " + id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
