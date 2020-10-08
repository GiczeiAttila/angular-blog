package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.dto.RegistrationFormInitData;
import com.progmasters.reactblog.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "localhost:4200")
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {
    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping()
    public ResponseEntity<RegistrationFormInitData> getFormInitData() {
        return new ResponseEntity<>(userService.createRegistrationInitData(), HttpStatus.OK);
    }
}
