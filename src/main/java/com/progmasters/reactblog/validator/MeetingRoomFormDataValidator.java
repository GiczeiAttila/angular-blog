package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.dto.MeetingRoomFormData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MeetingRoomFormDataValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return MeetingRoomFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MeetingRoomFormData meetingRoomFormData = (MeetingRoomFormData) o;
        String name = meetingRoomFormData.getName();
        int seats = meetingRoomFormData.getSeats();

        if (name.isEmpty() || name == null) {
            errors.rejectValue("name", "meetingRoomFormData.name.empty");
        } else if (Character.isLowerCase(name.charAt(1))) {
            errors.rejectValue("name", "meetingRoomFormData.name.lowercase");
        }

        if (seats < 2 || meetingRoomFormData.getSeats() == null) {
            errors.rejectValue("seats", "meetingRoomFormData.seats.not-enough");
        }

    }
}
