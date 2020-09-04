package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.SuggestionStatusEnum;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.SuggestionFormDto;
import com.progmasters.reactblog.domain.dto.SuggestionListItemDto;
import com.progmasters.reactblog.domain.dto.SuggestionStatusChangeDto;
import com.progmasters.reactblog.domain.dto.SuggestionVoteDto;
import com.progmasters.reactblog.repository.SuggestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class SuggestionService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserService userService;
    private final SuggestionRepository suggestionRepository;
    private final EmailSenderService emailSenderService;

    @Autowired
    public SuggestionService(UserService userService, SuggestionRepository suggestionRepository, EmailSenderService emailSenderService) {
        this.userService = userService;
        this.suggestionRepository = suggestionRepository;
        this.emailSenderService = emailSenderService;
    }

    public Suggestion saveSuggestion(SuggestionFormDto suggestionFormDto) {
        User user = userService.findById(suggestionFormDto.getUserId());
        Suggestion suggestion = suggestionRepository.save(new Suggestion(suggestionFormDto, user));
        if (suggestion.getId() != null) {
            List<User> userList = userService.findAllUsersWithStatus(UserStatusEnum.ACTIVE);
            logger.info("Created suggestion with user id:" + suggestion.getUser().getId() + ", suggestion id: " + suggestion.getId() + " and suggestion title:" + suggestion.getTitle());
            emailSenderService.sendNewSuggestionNotificationEmail(suggestion, userList);
        }
        return suggestion;
    }

    public Suggestion saveVote(SuggestionVoteDto suggestionVoteDto) {
        return null;
    }

    public List<SuggestionListItemDto> getSuggestions() {
        List<SuggestionListItemDto> suggestionListItemDtoList = suggestionRepository.findAll()
                .stream()
                .map(suggestion -> new SuggestionListItemDto(suggestion)).collect(Collectors.toList());
        return suggestionListItemDtoList;
    }

    public void changeSuggestionStatus(SuggestionStatusChangeDto suggestionStatusChangeDto) {
        Long suggestionId = suggestionStatusChangeDto.getSuggestionId();
        Optional<Suggestion> optionalSuggestion = suggestionRepository.findById(suggestionId);
        if (optionalSuggestion.isPresent()){
            Suggestion suggestion = optionalSuggestion.get();
            suggestion.setStatus(SuggestionStatusEnum.valueOf(suggestionStatusChangeDto.getStatus()));
        }
    }
}
