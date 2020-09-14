package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.TimeOffDateRange;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.TimeOffFormData;
import com.progmasters.reactblog.domain.dto.UserTimeOffList;
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
}
