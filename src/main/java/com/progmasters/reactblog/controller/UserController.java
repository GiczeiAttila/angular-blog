package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.dto.UserFormDto;
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
    @Autowired
    public UserController(UserService userService, UserFormDtoValidator userFormDtoValidator) {
        this.userService = userService;
        this.userFormDtoValidator = userFormDtoValidator;
    }

    @InitBinder("userFormDto")
    private void init(WebDataBinder binder){
        binder.addValidators(userFormDtoValidator);
    }

    @PostMapping("create")
    public ResponseEntity<Void> createUser(@Valid @RequestBody UserFormDto userFormDto){
        userService.createUser(userFormDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
