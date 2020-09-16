package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.SuggestionFormDto;
import com.progmasters.reactblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SuggestionValidator implements Validator {
    private final UserService userService;

    @Autowired
    public SuggestionValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SuggestionFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SuggestionFormDto suggestionFormDto = (SuggestionFormDto) target;

        Long id = suggestionFormDto.getUserId();
        User user = userService.findById(id);
        if (user == null) {
            errors.rejectValue("id", "suggestionFormDto.not-authorised");
        }else if (user.getUserStatus() != UserStatusEnum.ACTIVE) {
            errors.rejectValue("id", "suggestionFormDto.not-authorised");
        }
        if (suggestionFormDto.getTitle() == null || suggestionFormDto.getTitle().isEmpty()) {
            errors.rejectValue("title", "suggestionFormDto.title.empty");
        } else if (suggestionFormDto.getTitle().length() > 250 || suggestionFormDto.getTitle().length() < 3) {
            errors.rejectValue("title", "suggestionFormDto.title.length-error");
        }
        if (suggestionFormDto.getDescription() == null || suggestionFormDto.getDescription().isEmpty()) {
            errors.rejectValue("description", "suggestionFormDto.postBody.empty");
        }

    }
}
