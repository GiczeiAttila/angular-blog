package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.UserLogInFormDto;
import com.progmasters.reactblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class LoginValidator implements Validator {

    private final UserService userService;

    @Autowired
    public LoginValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserLogInFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserLogInFormDto userLogInFormDto = (UserLogInFormDto) target;
        User user = userService.findById(userLogInFormDto.getId());
        if (user == null) {
            errors.rejectValue("password", "userLoginFormDto.wrong-login-data");
        } else {
            if (user.getUserStatus() == UserStatusEnum.BLOCKED) {
                errors.rejectValue("password", "userLoginFormDto.account-blocked");
                //           } else if (!user.getPassword().equals(userLogInFormDto.getPassword())) {
                //               errors.rejectValue("password", "userLoginFormDto.wrong-login-data");
                //               user.setNumberOfLoginAttempts(user.getNumberOfLoginAttempts() + 1);
            } else if (user.getNumberOfLoginAttempts() < 3) {
                user.setNumberOfLoginAttempts(0L);
            }
            if (user.getNumberOfLoginAttempts() >= 3) {
                user.setUserStatus(UserStatusEnum.BLOCKED);
            }
            userService.saveUser(user);
        }

    }
}
