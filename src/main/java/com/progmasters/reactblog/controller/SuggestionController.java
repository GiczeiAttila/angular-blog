package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.dto.SuggestionFormDto;
import com.progmasters.reactblog.service.SuggestionService;
import com.progmasters.reactblog.validator.SuggestionValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    private static final Logger logger = LoggerFactory.getLogger(SuggestionController.class);
    private final SuggestionService suggestionService;
    private final SuggestionValidator suggestionValidator;

    @Autowired
    public SuggestionController(SuggestionService suggestionService, SuggestionValidator suggestionValidator) {
        this.suggestionService = suggestionService;
        this.suggestionValidator = suggestionValidator;
    }

    @InitBinder("suggestionFormDto")
    private void initSuggestionValidator(WebDataBinder binder) {
        binder.addValidators(suggestionValidator);
    }

    @PostMapping
    public ResponseEntity<Void> createSuggestion(@Valid @RequestBody SuggestionFormDto suggestionFormDto, HttpSession session) {
        logger.info("Create suggestion requested with user id:" + suggestionFormDto.getUserId() + " with suggestion title: " + suggestionFormDto.getTitle());
        Suggestion suggestion = suggestionService.saveSuggestion(suggestionFormDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
