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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        if (user == null) {
            errors.rejectValue("id", "openPositionFormDto.not-authorised");
        }else if (user.getUserStatus() != UserStatusEnum.ACTIVE) {
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
        Date today;
        Date maxDeadline;
        Date deadline;
        if (openPositionFormDto.getDeadline() == null || openPositionFormDto.getDeadline().isEmpty()) {
            errors.rejectValue("deadline", "openPositionFormDto.deadline.required");
        }else {
            try {
                String date = format.format(new Date());
                today = format.parse(date);
                Calendar c = Calendar.getInstance();
                c.setTime(today);
                c.add(Calendar.YEAR, 1);
                maxDeadline = c.getTime();
                deadline = format.parse(openPositionFormDto.getDeadline());
                if (!deadline.after(today) || deadline.after(maxDeadline)){
                    errors.rejectValue("deadline", "openPositionFormDto.deadline");
                }
            } catch (ParseException e) {
                errors.rejectValue("deadline", "openPositionFormDto.deadline.wrongDate");
            }
        }
    }
}
