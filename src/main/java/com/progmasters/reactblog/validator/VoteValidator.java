package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.dto.SuggestionVoteDto;
import com.progmasters.reactblog.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class VoteValidator implements Validator {

    private final UserService userService;

    public VoteValidator(UserService userService) {
        this.userService = userService;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return SuggestionVoteDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
//        SuggestionVoteDto suggestionVoteDto = (SuggestionVoteDto) target;
//        Long id = suggestionVoteDto.getVotingUserId();
//        User user = userService.findById(id);
//        List<Vote>
//        if (user == null) {
//            errors.rejectValue("id", "voteDto.not-authorised");
//        }else if (user.getUserStatus() != UserStatusEnum.ACTIVE) {
//            errors.rejectValue("password", "voteDto.not-authorised");
//        }
    }
}
