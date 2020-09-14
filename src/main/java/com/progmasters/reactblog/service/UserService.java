package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.PasswordDto;
import com.progmasters.reactblog.domain.dto.UserConfirmationDto;
import com.progmasters.reactblog.domain.dto.UserFormDto;
import com.progmasters.reactblog.repository.SuggestionRepository;
import com.progmasters.reactblog.repository.TimeOffDateRangeRepository;
import com.progmasters.reactblog.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    private final SuggestionRepository suggestionRepository;
    private final EmailSenderService emailSenderService;
    private final TimeOffDateRangeRepository timeOffDateRangeRepository;

    public UserService(UserRepository userRepository, SuggestionRepository suggestionRepository, EmailSenderService emailSenderService, TimeOffDateRangeRepository timeOffDateRangeRepository) {
        this.userRepository = userRepository;
        this.suggestionRepository = suggestionRepository;
        this.emailSenderService = emailSenderService;
        this.timeOffDateRangeRepository = timeOffDateRangeRepository;
    }

    public User createUser(UserFormDto userFormDto) {
        User user = new User(userFormDto);
        userRepository.save(user);
        emailSenderService.sendRegistrationConfirmationEmail(user.getEmail(), user.getToken(), user.getId());
        logger.info("User created with id: " + user.getId());
        return user;
    }

    public boolean idIsTaken(Long id) {
        return userRepository.findById(id).isPresent();
    }

    public User confirmRegistration(UserConfirmationDto userConfirmationDto) {
        Optional<User> userOptional = userRepository.findById(userConfirmationDto.getId());
        User user = userOptional.get();
        user.setUserStatus(UserStatusEnum.ACTIVE);
        user.setPassword(userConfirmationDto.getPassword());
        user.setToken(null);
        logger.info("User confirmed with id: " + userConfirmationDto.getId() + "and with token: " + userConfirmationDto.getToken());
        emailSenderService.sendConfirmationSuccessfulEmail(user.getEmail(), user.getId());
        return user;
    }

    public void savePassword(PasswordDto passwordDto) {
        Optional<User> userOptional = userRepository.findById(passwordDto.getId());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if (user.getPassword() == passwordDto.getOldPassword() && user.getUserStatus()==UserStatusEnum.BLOCKED){
                user.setPassword(passwordDto.getPassword());
                user.setUserStatus(UserStatusEnum.ACTIVE);
                logger.info("New password saved for id: " + passwordDto.getId());
            }
        }
    }

    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
        logger.info("New user saved with id: " + user.getId());
    }

    public List<User> findAllUsersWithStatus(UserStatusEnum status) {
        return userRepository.findAllByWithActiveStatus(status);
    }

}
