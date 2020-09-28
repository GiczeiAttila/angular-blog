package com.progmasters.reactblog.validator;

import com.progmasters.reactblog.domain.dto.MeetingReservationFormData;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.ZonedDateTime;
import java.util.List;

import static com.progmasters.reactblog.utils.DateUtils.convertLocalDateTimeToZonedDateTime;

@Component
public class MeetingReservationFormDataValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return MeetingReservationFormData.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MeetingReservationFormData meetingFormData = (MeetingReservationFormData) o;
        String title = meetingFormData.getTitle();
        String description = meetingFormData.getDescription();
        List<Long> participants = meetingFormData.getParticipantsId();
        Long meetingRoom = meetingFormData.getMeetingRoomId();

        if (title == null || title.isEmpty()) {
            errors.rejectValue("title", "meetingReservationFormData.title.empty");
        }

        if (description == null || description.isEmpty()) {
            errors.rejectValue("description", "meetingReservationFormData.description.empty");
        }


        if (participants == null || participants.isEmpty()) {
            errors.rejectValue("participantsId", "meetingReservationFormData.participantsId.null");
        }

        if (meetingRoom == null) {
            errors.rejectValue("meetingRoomId", "meetingReservationFormData.meetingRoomId.null");
        }

        ZonedDateTime startDateTime = convertLocalDateTimeToZonedDateTime(meetingFormData.getStartDateTime());
        ZonedDateTime endDateTime = convertLocalDateTimeToZonedDateTime(meetingFormData.getEndDateTime());
        ZonedDateTime now = ZonedDateTime.now();

        if (!startDateTime.isBefore(now)) {
            errors.rejectValue("endDate", "meetingReservationFormData.endDate.wrong-startDate");
        } else if (!startDateTime.isBefore(endDateTime)) {
            errors.rejectValue("endDate", "meetingReservationFormData.endDate.before");
        }

       /* DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date startDate;
        Date endDate;
        Date today;

        try {
            String date = format.format(new Date());
            today = format.parse(date);
            startDate = format.parse(meetingFormData.getStartDate());
            String endDateDay = meetingFormData.getStartDate().substring(0, 12);
            String endDateWithTime = endDateDay + meetingFormData.getEndDate().substring(12);
            endDate = format.parse(endDateWithTime);
            if (startDate.before(today)) {
                errors.rejectValue("endDate", "meetingReservationFormData.endDate.wrong-startDate");
            }
        } catch (ParseException e) {
            errors.rejectValue("endDate", "meetingReservationFormData.endDate.invalid");
        }

        */

    }
}
