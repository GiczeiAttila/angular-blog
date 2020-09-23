package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.UserStatusEnum;
import com.progmasters.reactblog.domain.dto.OpenPositionFormDto;
import com.progmasters.reactblog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.TimeZone;

@Component
public class OpenPositionFormDtoValidator implements Validator {

    private final UserService userService;

    @Autowired
    public OpenPositionFormDtoValidator(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return OpenPositionFormDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        OpenPositionFormDto openPositionFormDto = (OpenPositionFormDto) target;

        Long id = openPositionFormDto.getUserId();
        User user = userService.findById(id);
        if (user == null || user.getUserStatus() != UserStatusEnum.ACTIVE) {
            errors.rejectValue("id", "openPositionFormDto.not-authorised");
        }

        if (openPositionFormDto.getPositionName() == null || openPositionFormDto.getPositionName().isEmpty()) {
            errors.rejectValue("positionName", "openPositionFormDto.positionName.empty");
        } else if (openPositionFormDto.getPositionName().length() > 250 || openPositionFormDto.getPositionName().length() < 3) {
            errors.rejectValue("positionName", "openPositionFormDto.positionName.length-error");
        }
        if (openPositionFormDto.getDescription() == null || openPositionFormDto.getDescription().isEmpty()) {
            errors.rejectValue("description", "openPositionFormDto.description");
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        if (openPositionFormDto.getDeadline() == null) {
            errors.rejectValue("deadline", "openPositionFormDto.deadline.required");
        } else {
            LocalDate deadline = openPositionFormDto.getDeadline();

            if (!deadline.isAfter(LocalDate.now()) || deadline.isAfter(LocalDate.now().plusYears(1))) {
                errors.rejectValue("deadline", "openPositionFormDto.deadline");
            }

        }
    }

}
