package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.UserFormDto;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserFormDto userFormDto) {
        User user = new User(userFormDto);
        userRepository.save(user);
    }

    public boolean idIsTaken(Long id) {
        return userRepository.findById(id).isPresent();
    }
}
