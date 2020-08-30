package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.UserConfirmationDto;
import com.progmasters.reactblog.domain.dto.UserFormDto;
import com.progmasters.reactblog.service.EmailSenderService;
import com.progmasters.reactblog.service.UserService;
import com.progmasters.reactblog.validator.UserFormDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserFormDtoValidator userFormDtoValidator;
    private final EmailSenderService emailSenderService;

    @Autowired
    public UserController(UserService userService, UserFormDtoValidator userFormDtoValidator, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.userFormDtoValidator = userFormDtoValidator;
        this.emailSenderService = emailSenderService;
    }

    @InitBinder("userFormDto")
    private void init(WebDataBinder binder) {
        binder.addValidators(userFormDtoValidator);
    }

    @PostMapping("create")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserFormDto userFormDto) {
        User user = userService.createUser(userFormDto);
        emailSenderService.sendRegistrationConfirmationEmail(user.getEmail(), user.getToken(), user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("confirmation")
    public ResponseEntity<Void> confirmUserAccount(@RequestBody UserConfirmationDto userConfirmationDto) {
        userService.confirmRegistration(userConfirmationDto.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
