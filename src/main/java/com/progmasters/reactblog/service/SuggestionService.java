package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.*;
import com.progmasters.reactblog.domain.dto.SuggestionFormDto;
import com.progmasters.reactblog.domain.dto.SuggestionListItemDto;
import com.progmasters.reactblog.domain.dto.SuggestionStatusChangeDto;
import com.progmasters.reactblog.domain.dto.SuggestionVoteDto;
import com.progmasters.reactblog.repository.SuggestionRepository;
import com.progmasters.reactblog.repository.SuggestionVoteRepository;
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
    private final SuggestionVoteRepository suggestionVoteRepository;

    @Autowired
    public SuggestionService(UserService userService, SuggestionRepository suggestionRepository, EmailSenderService emailSenderService, SuggestionVoteRepository suggestionVoteRepository) {
        this.userService = userService;
        this.suggestionRepository = suggestionRepository;
        this.emailSenderService = emailSenderService;
        this.suggestionVoteRepository = suggestionVoteRepository;
    }

    public Suggestion saveSuggestion(SuggestionFormDto suggestionFormDto) {
        User user = userService.findById(suggestionFormDto.getUserId());
        if (user==null){
            logger.info("Creat suggestion with user id: " + suggestionFormDto.getUserId() + " failed because there are no user with given id");
            return null;
        }
        Suggestion suggestion = suggestionRepository.save(new Suggestion(suggestionFormDto, user));
        if (suggestion.getId() != null) {
            List<User> userList = userService.findAllUsersWithStatus(UserStatusEnum.ACTIVE);
            logger.info("Created suggestion with user id:" + suggestion.getUser().getId() + ", suggestion id: " + suggestion.getId() + " and suggestion title:" + suggestion.getTitle());
            emailSenderService.sendNewSuggestionNotificationEmail(suggestion, userList);
        }
        return suggestion;
    }

    public SuggestionVote saveVote(SuggestionVoteDto suggestionVoteDto) {
        Optional<Suggestion> suggestionOptional = suggestionRepository.findById(suggestionVoteDto.getSuggestionId());
        if (suggestionOptional.isPresent()){
            SuggestionVote suggestionVote;
            Suggestion suggestion = suggestionOptional.get();
            Optional<SuggestionVote> optionalSuggestionVote = suggestionVoteRepository.findVoteByUserIdAndSuggestionId(suggestionVoteDto.getVotingUserId(), suggestionVoteDto.getSuggestionId());
            if (optionalSuggestionVote.isPresent()){
                suggestionVote = optionalSuggestionVote.get();
                suggestionVote.setVote(VoteType.valueOf(suggestionVoteDto.getVote()));
            }else{
                User user = userService.findById(suggestionVoteDto.getVotingUserId());
                suggestionVote = new SuggestionVote(suggestionVoteDto,suggestion,user);
                suggestionVoteRepository.save(suggestionVote);
            }
            logger.info("Suggestion with id: " + suggestion.getId() +
                    " has received a vote");
            return suggestionVote;
        }
        return null;
    }


    public List<SuggestionListItemDto> getSuggestions() {
        List<SuggestionListItemDto> suggestionListItemDtoList = suggestionRepository.findAll()
                .stream()
                .map(suggestion -> new SuggestionListItemDto(suggestion)).collect(Collectors.toList());
        logger.info("Suggestion list requested");
        return suggestionListItemDtoList;
    }

    public void changeSuggestionStatus(SuggestionStatusChangeDto suggestionStatusChangeDto) {
        Long suggestionId = suggestionStatusChangeDto.getSuggestionId();
        Optional<Suggestion> optionalSuggestion = suggestionRepository.findById(suggestionId);
        if (optionalSuggestion.isPresent()){
            Suggestion suggestion = optionalSuggestion.get();
            suggestion.setStatus(SuggestionStatusEnum.valueOf(suggestionStatusChangeDto.getStatus()));
            logger.info("Suggestion with id: " + suggestion.getId() +
                    " has been changed.The suggestion status is now: " +
                    suggestion.getStatus()+" New status set by user with id: " +
                    suggestionStatusChangeDto.getCurrentUserId());
            emailSenderService.sendNewSuggestionStatusChangeNotificationEmail(suggestion);
        }
    }

    public Suggestion findSuggestionById(Long suggestionId) {
       Optional<Suggestion> suggestionOptional = suggestionRepository.findById(suggestionId);
       if (suggestionOptional.isPresent()){
           return suggestionOptional.get();
       }else{
           return null;
       }
    }
}
