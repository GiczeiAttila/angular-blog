package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.UserConfirmationDto;
import com.progmasters.reactblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegistrationConfirmationValidator implements Validator {
    private final UserService userService;

    @Autowired
    public RegistrationConfirmationValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserConfirmationDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserConfirmationDto userConfirmationDto = (UserConfirmationDto) target;
        Long id = userConfirmationDto.getId();
        User user = userService.findById(id);
        if (user == null) {
            errors.rejectValue("id", "confirmationDto.wrong-id");
        } else if (!userConfirmationDto.getToken().equals(user.getToken())) {
            errors.rejectValue("token", "confirmationDto.wrong-token");
        } else if (!userConfirmationDto.getPassword().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
            errors.rejectValue("password", "userFormDto.wrong-password");
        } else if (!userConfirmationDto.getPassword().equals(userConfirmationDto.getPassword2())) {
            errors.rejectValue("password2", "userFormDto.not-same-password");
        } else if (user.getUserStatus() != UserStatusEnum.RESET && user.getUserStatus() != UserStatusEnum.REGISTERED) {
            errors.rejectValue("password", "userFormDto.not-authorised");
        }
    }
}
