package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.dto.TimeOffFormData;
import com.progmasters.reactblog.utils.DateUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.ZonedDateTime;

@Component
public class TimeOffFormDataValidator implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return TimeOffFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TimeOffFormData formData = (TimeOffFormData) o;

        ZonedDateTime startDate = DateUtils.convertLocalDateToZonedDateTime(formData.getStartDate());
        ZonedDateTime endDate = DateUtils.convertLocalDateToZonedDateTime(formData.getEndDate());
        ZonedDateTime now = ZonedDateTime.now();


        if (formData.getStartDate() == null) {
            errors.rejectValue("startDate", "timeOff.startDate.required");
        }

        if (formData.getEndDate() == null) {
            errors.rejectValue("endDate", "timeOff.endDate.required");
        } else {
            if (!formData.getStartDate().isAfter(LocalDate.now())) {
                //TODO if startDate is same date like the actual day
                // eg: formData.getStartDate() == 2020.09.21
                //      and today is 2020.09.21
                // isAfter will result in false
                errors.rejectValue("startDate", "timeOff.startDate.beforeNow");
            }
        }
    }

}
