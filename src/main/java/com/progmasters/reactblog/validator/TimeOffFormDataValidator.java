package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.dto.TimeOffFormData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Component
public class TimeOffFormDataValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return TimeOffFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TimeOffFormData date = (TimeOffFormData) o;


        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today;
        Date startDate;

        if (date.getStartDate() == null || date.getStartDate().isEmpty()) {
            errors.rejectValue("startDate", "timeOff.startDate.required");
        }

        if (date.getEndDate() == null || date.getEndDate().isEmpty()) {
            errors.rejectValue("endDate", "timeOff.endDate.required");
        }

       /*else {
            try {
                today = format.parse(String.valueOf(new Date()));
                startDate = format.parse(date.getStartDate());
                if (!startDate.after(today)) {
                    errors.rejectValue("startDate", "timeOff.startDate.beforeNow");
                }
            } catch (ParseException e) {
                errors.rejectValue("startDate", "timeOff.startDate.wrongDate");
            }
        }

        */

    }
}
