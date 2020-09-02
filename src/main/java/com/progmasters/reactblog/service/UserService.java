package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.PasswordDto;
import com.progmasters.reactblog.domain.dto.SuggestionFormDto;
import com.progmasters.reactblog.domain.dto.UserConfirmationDto;
import com.progmasters.reactblog.domain.dto.UserFormDto;
import com.progmasters.reactblog.repository.SuggestionRepository;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final SuggestionRepository suggestionRepository;

    public UserService(UserRepository userRepository, SuggestionRepository suggestionRepository) {
        this.userRepository = userRepository;
        this.suggestionRepository = suggestionRepository;
    }

    public User createUser(UserFormDto userFormDto) {
        User user = new User(userFormDto);
        userRepository.save(user);
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
        return user;
    }

    public void savePassword(PasswordDto passwordDto) {
        Optional<User> userOptional = userRepository.findById(passwordDto.getId());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if (user.getToken() == null && user.getUserStatus()==UserStatusEnum.ACTIVE){
                user.setPassword(passwordDto.getPassword());
                user.setUserStatus(UserStatusEnum.ACTIVE);
            }
        }
    }

    public User findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public Suggestion saveSuggestion(SuggestionFormDto suggestionFormDto){
        User user = findById(suggestionFormDto.getUserId());
        return suggestionRepository.save(new Suggestion(suggestionFormDto, user));
    }
}
