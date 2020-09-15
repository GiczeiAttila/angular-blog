package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.OpenPosition;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.OpenPositionFormDto;
import com.progmasters.reactblog.repository.OpenPositionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OpenPositionService {
    private static final Logger logger = LoggerFactory.getLogger(OpenPositionService.class);
    private final UserService userService;
    private final EmailSenderService emailSenderService;
    private final OpenPositionRepository openPositionRepository;

    public OpenPositionService(UserService userService, EmailSenderService emailSenderService, OpenPositionRepository openPositionRepository) {
        this.userService = userService;
        this.emailSenderService = emailSenderService;
        this.openPositionRepository = openPositionRepository;
    }

    public OpenPosition saveOpenPosition(OpenPositionFormDto openPositionFormDto) {
        User user = userService.findById(openPositionFormDto.getUserId());
        OpenPosition openPosition = openPositionRepository.save(new OpenPosition(openPositionFormDto, user));
        System.out.println(openPosition.getDeadline());
        if (openPosition.getId() != null) {
            List<User> userList = userService.findAllUsersWithStatus(UserStatusEnum.ACTIVE);
            logger.info("Created open position with user id:" + openPosition.getUser().getId() + ", open position id: " + openPosition.getId() + " and open position name:" + openPosition.getPositionName());
            emailSenderService.sendNewOpenPositionNotificationEmail(openPosition, userList);
        }
        return openPosition;
    }
}
