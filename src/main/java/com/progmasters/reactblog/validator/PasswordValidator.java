package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.PasswordDto;
import com.progmasters.reactblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordValidator implements Validator {

    private final UserService userService;

    @Autowired
    public PasswordValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordDto passwordDto = (PasswordDto) target;
        User user = userService.findById(passwordDto.getId());
        if (user == null) {
            errors.rejectValue("password", "userFormDto.id-error");
        }else if (!user.getPassword().equals(passwordDto.getOldPassword())) {
            errors.rejectValue("password", "userFormDto.wrong-old-password");
        }else if (!passwordDto.getPassword().matches("(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$")) {
            errors.rejectValue("password", "userFormDto.wrong-password");
        }else if (!passwordDto.getPassword().equals(passwordDto.getPassword2())){
            errors.rejectValue("password2", "userFormDto.not-same-password");
        }else if (user.getUserStatus() != UserStatusEnum.ACTIVE){
            errors.rejectValue("password", "userFormDto.not-authorised");
        }
    }
}
