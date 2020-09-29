package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.TimeOffFormData;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZonedDateTime;

import static com.progmasters.reactblog.utils.DateUtils.convertLocalDateToZonedDateTime;

@Entity
@Table(name = "timeOffDateRange")
public class TimeOffDateRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO Ide nem rakunk JSONFormat annotációt, az csak akkor kell amikor konverziót csinálunk
    // JSON-ból/DTO-ból oda/vissza,
    // mivel ez egy entitás ennek a Controllerig sose kéne eljutnia -> soha nem fogjuk küldeni / fogadni.
    @Column(name = "startDate")
    private ZonedDateTime startDate;

    @Column(name = "endDate")
    private ZonedDateTime endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "weekDays")
    private Integer weekDays;

    @Enumerated(EnumType.STRING)
    private TimeOffStatusEnum status;

    public TimeOffDateRange() {
    }

    public TimeOffDateRange(TimeOffFormData timeOffFormData, User user) {
        this.weekDays = calculateWeekDays(timeOffFormData.getStartDate(), timeOffFormData.getEndDate());
        this.user = user;
        this.status = TimeOffStatusEnum.PENDING;
        this.startDate = convertLocalDateToZonedDateTime(timeOffFormData.getStartDate());
        this.endDate = convertLocalDateToZonedDateTime(timeOffFormData.getEndDate());
    }

    //doesnt work yet
    //TODO How about now? :) Btw, írtam rá tesztet is :)
    private Integer calculateWeekDays(LocalDate startDate, LocalDate endDate) {
        Integer countWeekDays = 0;
        LocalDate temporalDate = startDate;
        while (!temporalDate.isAfter(endDate)) {
            DayOfWeek dayOfWeek = temporalDate.getDayOfWeek();
            boolean dateIsWeekday = dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
            if (dateIsWeekday) {
                countWeekDays++;
            }

            temporalDate = temporalDate.plusDays(1);
        }
        return countWeekDays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(ZonedDateTime startDate) {
        this.startDate = startDate;
    }

    public ZonedDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(ZonedDateTime endDate) {
        this.endDate = endDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getWeekDays() {
        return weekDays;
    }

    public void setWeekDays(Integer weekDays) {
        this.weekDays = weekDays;
    }

    public TimeOffStatusEnum getStatus() {
        return status;
    }

    public void setStatus(TimeOffStatusEnum status) {
        this.status = status;
    }

}
