package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.Suggestion;
import com.progmasters.reactblog.domain.SuggestionStatusEnum;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.SuggestionVoteDto;
import com.progmasters.reactblog.service.SuggestionService;
import com.progmasters.reactblog.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VoteValidator implements Validator {

    private final UserService userService;
    private final SuggestionService suggestionService;

    public VoteValidator(UserService userService, SuggestionService suggestionService) {
        this.userService = userService;
        this.suggestionService = suggestionService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return SuggestionVoteDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SuggestionVoteDto suggestionVoteDto = (SuggestionVoteDto) target;
        Long votingUserId = suggestionVoteDto.getVotingUserId();
        User votingUser = userService.findById(votingUserId);
        Suggestion suggestion = suggestionService.findSuggestionById(suggestionVoteDto.getSuggestionId());
        if (suggestion == null){
            errors.reject("voteDto.wrong-suggestion-id");
        }else if (suggestion.getStatus() != SuggestionStatusEnum.ACTIVE){
            errors.reject("voteDto.not-authorised");
        }else if (votingUser == null) {
            errors.rejectValue("currentUserId", "voteDto.not-authorised");
        }else if (votingUserId == suggestionVoteDto.getUserId()){
            errors.rejectValue("currentUserId", "voteDto.not-authorised");
        }else if (!(suggestionVoteDto.getVote().equals("UP") || suggestionVoteDto.getVote().equals("DOWN"))){
            errors.reject("voteDto.wrong-vote");
        }
    }
}
