package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.PasswordDto;
import com.progmasters.reactblog.domain.dto.UserConfirmationDto;
import com.progmasters.reactblog.domain.dto.UserFormDto;
import com.progmasters.reactblog.domain.dto.UserLogInFormDto;
import com.progmasters.reactblog.service.EmailSenderService;
import com.progmasters.reactblog.service.UserService;
import com.progmasters.reactblog.validator.LoginValidator;
import com.progmasters.reactblog.validator.PasswordValidator;
import com.progmasters.reactblog.validator.RegistrationConfirmationValidator;
import com.progmasters.reactblog.validator.UserFormDtoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserFormDtoValidator userFormDtoValidator;
    private final EmailSenderService emailSenderService;
    private final RegistrationConfirmationValidator registrationConfirmationValidator;
    private final PasswordValidator passwordValidator;
    private final LoginValidator loginValidator;

    @Autowired
    public UserController(UserService userService, PasswordValidator passwordValidator, UserFormDtoValidator userFormDtoValidator, EmailSenderService emailSenderService, RegistrationConfirmationValidator registrationConfirmationValidator, LoginValidator loginValidator) {
        this.userService = userService;
        this.userFormDtoValidator = userFormDtoValidator;
        this.emailSenderService = emailSenderService;
        this.registrationConfirmationValidator = registrationConfirmationValidator;
        this.passwordValidator = passwordValidator;
        this.loginValidator = loginValidator;
    }

    @InitBinder("userFormDto")
    private void initUserFormValidator(WebDataBinder binder) {
        binder.addValidators(userFormDtoValidator);
    }

    @InitBinder("userConfirmationDto")
    private void initTokenValidator(WebDataBinder binder) {
        binder.addValidators(registrationConfirmationValidator);
    }

    @InitBinder("passwordDto")
    private void initPasswordValidator(WebDataBinder binder) {
        binder.addValidators(passwordValidator);
    }

    @InitBinder("userLogInFormDto")
    private void initLoginValidator(WebDataBinder binder) {
        binder.addValidators(loginValidator);
    }

    @PostMapping("create")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserFormDto userFormDto) {
        User user = userService.createUser(userFormDto);
        emailSenderService.sendRegistrationConfirmationEmail(user.getEmail(), user.getToken(), user.getId());
        logger.info("User created with id: " + user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("confirmation")
    @Async
    public ResponseEntity<Void> confirmUserAccount(@Valid @RequestBody UserConfirmationDto userConfirmationDto) {
        User user = userService.confirmRegistration(userConfirmationDto);
        emailSenderService.sendConfirmationSuccessfulEmail(user.getEmail(), user.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("password")
    public ResponseEntity<Void> savePassword(@Valid @RequestBody PasswordDto passwordDto) {
        userService.savePassword(passwordDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("login")
    public ResponseEntity<Void> login(@Valid @RequestBody UserLogInFormDto userLogInFormDto, HttpSession session) {
        session.setAttribute("user_id", userLogInFormDto.getId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        session.invalidate();
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
