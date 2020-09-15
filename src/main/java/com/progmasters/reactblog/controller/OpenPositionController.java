package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.OpenPosition;
import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.dto.OpenPositionFormDto;
import com.progmasters.reactblog.domain.dto.SuggestionFormDto;
import com.progmasters.reactblog.service.OpenPositionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/open-positions")
public class OpenPositionController {
    private static final Logger logger = LoggerFactory.getLogger(OpenPositionController.class);
    private final OpenPositionService openPositionService;

    public OpenPositionController(OpenPositionService openPositionService) {
        this.openPositionService = openPositionService;
    }

    @PostMapping
    public ResponseEntity<Void> createSuggestion(@RequestBody OpenPositionFormDto openPositionFormDto) {
        logger.info("Create open position requested with user id:" + openPositionFormDto.getUserId() +
                " with open position name: " + openPositionFormDto.getPositionName() +
                " deadline: " + openPositionFormDto.getDeadline());
        OpenPosition openPosition = openPositionService.saveOpenPosition(openPositionFormDto);
        System.out.println(openPosition.getDeadline());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
