package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.TimeOffDateRange;
import com.progmasters.reactblog.domain.TimeOffStatusEnum;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.*;
import com.progmasters.reactblog.repository.TimeOffDateRangeRepository;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RequestService {

    private final TimeOffDateRangeRepository timeOffDateRangeRepository;
    private final UserRepository userRepository;

    @Autowired
    public RequestService(TimeOffDateRangeRepository timeOffDateRangeRepository, UserRepository userRepository) {
        this.timeOffDateRangeRepository = timeOffDateRangeRepository;
        this.userRepository = userRepository;
    }

    public void saveTimeOffDate(TimeOffFormData timeOffFormData) {
        Optional<User> optionalUser = userRepository.findById(timeOffFormData.getUserId());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            timeOffDateRangeRepository.save(new TimeOffDateRange(timeOffFormData, user));
        }
    }

    public User findUserById(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User actualUser = optionalUser.get();
            return actualUser;
        } else {
            return null;
        }
    }

    public List<UserTimeOffList> getUserTimeOffListById(Long id) {
        List<UserTimeOffList> list = this.timeOffDateRangeRepository.findTimeOffsByUserId(id)
                .stream()
                .map(timeOff -> new UserTimeOffList(timeOff))
                .collect(Collectors.toList());
        return list;
    }


    public List<TimeOffListItem> getAllTimeOffList() {
        List<TimeOffListItem> listOfAll = this.timeOffDateRangeRepository.findAll()
                .stream()
                .map(timeOff -> new TimeOffListItem(timeOff))
                .collect(Collectors.toList());
        return listOfAll;
    }

    public void changeTimeOffStatus(TimeOffStatusChangeDto timeOffStatusChangeDto) {
        Long timeOffId = timeOffStatusChangeDto.getDateId();
        Optional<TimeOffDateRange> optionalTimeOffDateRange = this.timeOffDateRangeRepository.findById(timeOffId);
        if (optionalTimeOffDateRange.isPresent()) {
            TimeOffDateRange timeOff = optionalTimeOffDateRange.get();
            timeOff.setStatus(TimeOffStatusEnum.valueOf(timeOffStatusChangeDto.getStatus()));
        }
    }

    public List<CalendarAcceptedTimeOffListDto> getAcceptedTimeOffDates(Long id) {
        TimeOffStatusEnum status = TimeOffStatusEnum.valueOf("ACCEPTED");
        List<CalendarAcceptedTimeOffListDto> listOfAll = this.timeOffDateRangeRepository.findAccepted(status)
                .stream()
                .map(timeOff -> new CalendarAcceptedTimeOffListDto(timeOff))
                .collect(Collectors.toList());
        return listOfAll;
    }

    public List<CalendarPendingTimeOffListDto> getPendingTimeOffDates(Long id) {
        TimeOffStatusEnum status = TimeOffStatusEnum.valueOf("PENDING");
        List<CalendarPendingTimeOffListDto> listOfAll = this.timeOffDateRangeRepository.findPending(status)
                .stream()
                .map(timeOff -> new CalendarPendingTimeOffListDto(timeOff))
                .collect(Collectors.toList());
        return listOfAll;
    }
}
