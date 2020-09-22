package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.SuggestionVote;
import com.progmasters.reactblog.domain.dto.SuggestionFormDto;
import com.progmasters.reactblog.domain.dto.SuggestionListItemDto;
import com.progmasters.reactblog.domain.dto.SuggestionStatusChangeDto;
import com.progmasters.reactblog.domain.dto.SuggestionVoteDto;
import com.progmasters.reactblog.service.SuggestionService;
import com.progmasters.reactblog.validator.SuggestionValidator;
import com.progmasters.reactblog.validator.VoteValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
public class SuggestionController {

    private static final Logger logger = LoggerFactory.getLogger(SuggestionController.class);
    private final SuggestionService suggestionService;
    private final SuggestionValidator suggestionValidator;
    private final VoteValidator voteValidator;

    @Autowired
    public SuggestionController(SuggestionService suggestionService, SuggestionValidator suggestionValidator, VoteValidator voteValidator) {
        this.suggestionService = suggestionService;
        this.suggestionValidator = suggestionValidator;
        this.voteValidator = voteValidator;
    }

    @InitBinder("suggestionFormDto")
    private void initSuggestionValidator(WebDataBinder binder) {
        binder.addValidators(suggestionValidator);
    }

    @InitBinder("suggestionVoteDto")
    private void initSuggestionVoteValidator(WebDataBinder binder) {
        binder.addValidators(voteValidator);
    }

    @PostMapping
    public ResponseEntity<Void> createSuggestion(@Valid @RequestBody SuggestionFormDto suggestionFormDto) {
        logger.info("Create suggestion requested with user id:" + suggestionFormDto.getUserId() + " with suggestion title: " + suggestionFormDto.getTitle());
        Suggestion suggestion = suggestionService.saveSuggestion(suggestionFormDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/vote")
    public ResponseEntity<Void> createVote(@Valid @RequestBody SuggestionVoteDto suggestionVoteDto) {
        logger.info("Create vote requested with user id:" + suggestionVoteDto.getVotingUserId() + " for suggestion with id: " + suggestionVoteDto.getSuggestionId());
        SuggestionVote suggestionVote = suggestionService.saveVote(suggestionVoteDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SuggestionListItemDto>> getSuggestions() {
        logger.info("Suggestion list requested");
        List<SuggestionListItemDto> suggestionListItemDtoList = suggestionService.getSuggestions();
        return new ResponseEntity<>(suggestionListItemDtoList, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> changeSuggestionStatus(@RequestBody SuggestionStatusChangeDto suggestionStatusChangeDto) {
        suggestionService.changeSuggestionStatus(suggestionStatusChangeDto);
        logger.info("Request to change suggestion status to: " +
                suggestionStatusChangeDto.getStatus() + "for suggestion with id: " +
                suggestionStatusChangeDto.getSuggestionId() + "by user with id: " +
                suggestionStatusChangeDto.getCurrentUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
