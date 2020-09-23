package com.progmasters.reactblog.domain;

import javax.persistence.*;

@Entity
public class ApplicantForOpenPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User applicant;

    @ManyToOne
    private OpenPosition openPosition;

    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus;

    public ApplicantForOpenPosition() {
    }

    public ApplicantForOpenPosition(User applicant, OpenPosition openPosition) {
        this.applicant = applicant;
        this.openPosition = openPosition;
        this.applicationStatus = ApplicationStatus.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public OpenPosition getOpenPosition() {
        return openPosition;
    }

    public void setOpenPosition(OpenPosition openPosition) {
        this.openPosition = openPosition;
    }

    public ApplicationStatus getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(ApplicationStatus applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

}
