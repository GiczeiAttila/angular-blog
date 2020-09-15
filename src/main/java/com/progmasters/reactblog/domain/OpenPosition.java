package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.OpenPositionFormDto;

import javax.persistence.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity
public class OpenPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String positionName;
    private String description;
    private Date deadline;

    @OneToMany(mappedBy = "openPosition")
    private List<ApplicantForOpenPosition> applicants;

    @ManyToOne
    private User user;

    public OpenPosition() {
    }

    public OpenPosition(OpenPositionFormDto openPositionFormDto, User user) {
        this.positionName = openPositionFormDto.getPositionName();
        this.description = openPositionFormDto.getDescription();
        System.out.println(openPositionFormDto.getDeadline());
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            this.deadline = format.parse(openPositionFormDto.getDeadline());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(this.deadline);
        this.applicants = new ArrayList<>();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public List<ApplicantForOpenPosition> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<ApplicantForOpenPosition> applicants) {
        this.applicants = applicants;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
