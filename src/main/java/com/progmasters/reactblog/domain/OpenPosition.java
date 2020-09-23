package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.OpenPositionFormDto;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.progmasters.reactblog.utils.DateUtils.convertLocalDateToZonedDateTime;

@Entity
public class OpenPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String positionName;
    @Lob
    private String description;
    private ZonedDateTime deadline;

    @OneToMany(mappedBy = "openPosition")
    private List<ApplicantForOpenPosition> applicants;

    @ManyToOne
    private User user;

    public OpenPosition() {
    }

    public OpenPosition(OpenPositionFormDto openPositionFormDto, User user) {
        this.positionName = openPositionFormDto.getPositionName();
        this.description = openPositionFormDto.getDescription();
        this.deadline = convertLocalDateToZonedDateTime(openPositionFormDto.getDeadline());
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

    public ZonedDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(ZonedDateTime deadline) {
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
