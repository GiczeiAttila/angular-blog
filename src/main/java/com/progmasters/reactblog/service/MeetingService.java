package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.*;
import com.progmasters.reactblog.domain.dto.*;
import com.progmasters.reactblog.repository.MeetingParticipantRepository;
import com.progmasters.reactblog.repository.MeetingReservationRepository;
import com.progmasters.reactblog.repository.MeetingRoomRepository;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MeetingService {

    private MeetingRoomRepository meetingRoomRepository;
    private MeetingReservationRepository meetingReservationRepository;
    private MeetingParticipantRepository meetingParticipantRepository;
    private UserRepository userRepository;
    private EmailSenderService emailSenderService;

    @Autowired
    public MeetingService(MeetingRoomRepository meetingRoomRepository, MeetingReservationRepository meetingReservationRepository, MeetingParticipantRepository meetingParticipantRepository, UserRepository userRepository, EmailSenderService emailSenderService) {
        this.meetingRoomRepository = meetingRoomRepository;
        this.meetingReservationRepository = meetingReservationRepository;
        this.meetingParticipantRepository = meetingParticipantRepository;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }


    public MeetingRoom saveMeetingRoom(MeetingRoomFormData meetingRoomFormData) {
        MeetingRoom meetingRoom = this.meetingRoomRepository.save(new MeetingRoom(meetingRoomFormData));
        return meetingRoom;
    }

    public MeetingReservation addMeetingReservation(MeetingReservationFormData meetingReservationFormData) {
        Optional<User> optionalCreator = this.userRepository.findById(meetingReservationFormData.getCreatorId());
        Optional<MeetingRoom> optionalMeetingRoom = this.meetingRoomRepository.findById(meetingReservationFormData.getMeetingRoomId());

        if (optionalCreator.isPresent() && optionalMeetingRoom.isPresent()) {
            User creator = optionalCreator.get();
            MeetingRoom meetingRoom = optionalMeetingRoom.get();
            MeetingReservation meetingReservation = this.meetingReservationRepository.save(new MeetingReservation(meetingReservationFormData, creator, meetingRoom));
            saveParticipants(meetingReservationFormData.getParticipantsId(), meetingReservation);
            return meetingReservation;
        } else {
            return null;
        }

    }

    private void saveParticipants(List<Long> participantsId, MeetingReservation meetingReservation) {
        for (Long id : participantsId) {
            Optional<User> optionalUser = this.userRepository.findById(id);
            if (optionalUser.isPresent()) {
                User user = optionalUser.get();
                MeetingParticipant meetingParticipant = new MeetingParticipant(user, meetingReservation);
                this.meetingParticipantRepository.save(meetingParticipant);
            }
        }
    }


    public List<MeetingRoomOptionDto> getMeetingRooms() {
        List<MeetingRoomOptionDto> meetingRooms = this.meetingRoomRepository.findAll()
                .stream()
                .map(room -> new MeetingRoomOptionDto(room))
                .collect(Collectors.toList());
        return meetingRooms;
    }

    public List<MeetingListItem> getMyMeetingList(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            List<MeetingListItem> meetingReservations = this.meetingParticipantRepository.findMeetingByUserId(optionalUser.get(), MeetingStatus.ACTIVE)
                    .stream()
                    .map(meeting -> new MeetingListItem(meeting.getMeetingReservation()))
                    .collect(Collectors.toList());
            return meetingReservations;
        } else {
            return null;
        }
    }

    public List<UserMeetingReservationListItem> getUserMeetingReservation(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            List<UserMeetingReservationListItem> meetingReservationList = this.meetingReservationRepository.findMeetingByCreatorId(optionalUser.get())
                    .stream()
                    .map(meeting -> {
                        List<MeetingParticipant> participants = this.meetingParticipantRepository.findUserByMeetingId(meeting);
                        return new UserMeetingReservationListItem(meeting, participants);
                    })
                    .collect(Collectors.toList());
            return meetingReservationList;
        } else {
            return null;
        }
    }

    public void changeMeetingStatus(MeetingStatusChangeDto statusChangeDto) {
        Optional<MeetingReservation> optionalMeetingReservation = this.meetingReservationRepository.findById(statusChangeDto.getMeetingId());
        if (optionalMeetingReservation.isPresent()) {
            MeetingReservation meetingReservation = optionalMeetingReservation.get();
            meetingReservation.setMeetingStatus(statusChangeDto.getStatus());
            List<User> participants = this.meetingParticipantRepository.findAllUserByMeetingId(meetingReservation.getId());
            emailSenderService.sendMeetingStatusChangeNotification(meetingReservation, participants);
        }
    }


    public MeetingReservationData getMeetingDataById(Long meetingId) {
        Optional<MeetingReservation> optionalMeeting = this.meetingReservationRepository.findById(meetingId);
        if (optionalMeeting.isPresent()) {
            List<MeetingParticipant> participants = this.meetingParticipantRepository.findUserByMeetingId(optionalMeeting.get());
            MeetingReservationData meeting = new MeetingReservationData(optionalMeeting.get(), participants);
            return meeting;
        } else {
            return null;
        }
    }

    public void updateMeeting(MeetingReservationUpdatedForm updatedForm) {
        Optional<MeetingReservation> optionalMeetingReservation = this.meetingReservationRepository.findById(updatedForm.getMeetingId());
        if (optionalMeetingReservation.isPresent()) {
            MeetingReservation meetingReservation = optionalMeetingReservation.get();
            meetingReservation.setTitle(updatedForm.getTitle());
            meetingReservation.setDescription(updatedForm.getDescription());
        }
    }
}
