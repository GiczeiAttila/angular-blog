package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.dto.TimeOffFormData;
import com.progmasters.reactblog.utils.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.DateFormat;
import java.text.ParseException;
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
        TimeOffFormData formData = (TimeOffFormData) o;


        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date today;
        Date startDate;

        if (formData.getStartDate() == null || formData.getStartDate().isEmpty()) {
            errors.rejectValue("startDate", "timeOff.startDate.required");
        }

        if (formData.getEndDate() == null || formData.getEndDate().isEmpty()) {
            errors.rejectValue("endDate", "timeOff.endDate.required");
        } else {
            try {
                String date = format.format(new Date());
                today = format.parse(date);
                startDate = format.parse(formData.getStartDate());
                if (!startDate.after(today)) {
                    errors.rejectValue("startDate", "timeOff.startDate.beforeNow");
                }
            } catch (ParseException e) {
                errors.rejectValue("startDate", "timeOff.startDate.wrongDate");
            }
        }
    }
}
