package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.UserFormDto;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(UserFormDto userFormDto) {
        User user = new User(userFormDto);
        userRepository.save(user);
        return user;
    }

    public boolean idIsTaken(Long id) {
        return userRepository.findById(id).isPresent();
    }

    public void confirmRegistration(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        User user = userOptional.get();
        user.setUserStatus(UserStatusEnum.ACTIVE);
        user.setToken(null);
    }
}
