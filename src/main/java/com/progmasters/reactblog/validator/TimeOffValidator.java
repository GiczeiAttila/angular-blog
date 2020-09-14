package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.dto.TimeOffFormData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class TimeOffValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TimeOffFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TimeOffFormData date = (TimeOffFormData) o;


        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = null;

        try {
            startDate = format.parse(date.getStartDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long millis = System.currentTimeMillis();
        Date today = new java.sql.Date(millis);


        if (today.before(startDate)) {
            errors.rejectValue("startDate", "timeOff.startDate.beforeNow");
        }

    }
}
