package com.progmasters.reactblog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.progmasters.reactblog.domain.dto.TimeOffFormData;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public TimeOffDateRange() {
    }

    public TimeOffDateRange(TimeOffFormData timeOffFormData, User user) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM.dd");
        try {
            this.startDate = format.parse(timeOffFormData.getStartDate());
            this.endDate = format.parse(timeOffFormData.getEndDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        this.user = user;
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
}
