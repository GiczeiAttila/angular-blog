package com.progmasters.reactblog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progmasters.reactblog.domain.dto.TimeOffFormData;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Table(name = "timeOffDateRange")
public class TimeOffDateRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "startDate")
    private Date startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "endDate")
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "weekDays")
    private Integer weekDays;

    public TimeOffDateRange() {
    }

    public TimeOffDateRange(TimeOffFormData timeOffFormData, User user) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.startDate = format.parse(timeOffFormData.getStartDate());
            this.endDate = format.parse(timeOffFormData.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.weekDays = calculateWeekDays(startDate, endDate);
        this.user = user;

    }

    //doesnt work yet
    private Integer calculateWeekDays(Date startDate, Date endDate) {
        Integer weekDays = 0;
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(startDate);
        end.setTime(endDate);
        end.add(Calendar.DATE, 1);

        while (start.before(end)) {
            if (Calendar.SATURDAY != start.get(Calendar.DAY_OF_WEEK)
                    || Calendar.SUNDAY != start.get(Calendar.DAY_OF_WEEK)) {
                weekDays++;
            }
            start.add(Calendar.DATE, 1);
        }
        return weekDays;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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


}
