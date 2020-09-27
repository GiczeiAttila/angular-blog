package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.PasswordDto;
import com.progmasters.reactblog.domain.dto.UserConfirmationDto;
import com.progmasters.reactblog.domain.dto.UserForMeetingOptionDto;
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
import java.util.stream.Collectors;

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

    //TODO Review - Mivel itt történik az email küldés, érdemes dobni egy @Async-et a methodra,
    // Ez külön szálra fogja rakni a futtatását... Ez azért lehet fontos, mert néha az
    // email küldés lehet akár 5-10 mp is, amit a FE be kell hogy várjon...
    public User confirmRegistration(UserConfirmationDto userConfirmationDto) {
        //TODO Review - a userRepository.findById methodot érdemes kiemelni egy service metódusba,
        // ami lekezeli, hogy valóban szerepel-e ott érték, és ha nem, akkor például dob exceptiont
        // Ezt utána szintén le lehet kezelni a GlobalExceptionHandlerben
        // Ebben az esetben ezt nem kell minden alkalommal kiírni... olvashatóbb, rövidebb lesz a kód
        Optional<User> userOptional = userRepository.findById(userConfirmationDto.getId());
        //TODO Review - Ilyet főleg ne csináljatok... gondolom ebben az esetben nem fordulhat elő
        // ( vagyis remélem ez a magyarázat mögötte )
        // De érdemes mindig megnézni, hogy az Optionalben van-e value...
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
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            //TODO Review - Ha lesz majd securityConfig, és ellenőrizni akarjátok a régi jelszót,
            // majd ne felejtsétek el hashelni mielőtt összehasonlítjátok! :)
            if (user.getPassword() == passwordDto.getOldPassword() && user.getUserStatus() == UserStatusEnum.BLOCKED) {
                user.setPassword(passwordDto.getPassword());
                user.setUserStatus(UserStatusEnum.ACTIVE);
                logger.info("New password saved for id: " + passwordDto.getId());
            }
        }
    }

    public User findById(Long id) {
        //TODO Review - valami ilyesmire gondoltam, kicsivel feljebb, de ha itt null-t adtok vissza,
        // akkor értelmét veszti az egész Optional...
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
        logger.info("New user saved with id: " + user.getId());
    }

    public List<User> findAllUsersWithStatus(UserStatusEnum status) {
        return userRepository.findAllByStatus(status);
    }

    public List<UserForMeetingOptionDto> getUsers() {

        List<UserForMeetingOptionDto> users = this.userRepository.findAll()
                                                                 .stream()
                                                                 .map(user -> new UserForMeetingOptionDto(user))
                                                                 .collect(Collectors.toList());
        return users;
    }

}
