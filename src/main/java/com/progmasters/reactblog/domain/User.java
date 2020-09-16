package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.UserFormDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String token;
    private Long numberOfLoginAttempts;

    @Enumerated(EnumType.STRING)
    private UserStatusEnum userStatus;


    @OneToMany(mappedBy = "author")
    private List<Post> myPosts;

    @OneToMany(mappedBy = "author")
    private List<Comment> myComments;

    @OneToMany(mappedBy = "user")
    private List<Suggestion> suggestionList;

    @OneToMany(mappedBy = "user")
    private List<SuggestionVote> suggestionVoteList;

    @OneToMany(mappedBy = "user")
    private List<TimeOffDateRange> timeOffDateRangeList;

    @OneToMany(mappedBy = "user")
    private List<OpenPosition> openPositionList;

    @OneToMany(mappedBy = "applicant")
    private List<ApplicantForOpenPosition> applicantForOpenPositionList;

    @OneToMany(mappedBy = "creator")
    private List<MeetingReservation> ownReservation;

    @OneToMany(mappedBy = "meetingReservation")
    private List<MeetingParticipent> allMeeting;

    public User() {
    }

    public User(UserFormDto userFormDto) {
        this.id = userFormDto.getId();
        this.firstName = userFormDto.getFirstName();
        this.lastName = userFormDto.getLastName();
        this.email = userFormDto.getEmail();
        this.phoneNumber = userFormDto.getPhoneNumber();
        this.userStatus = UserStatusEnum.REGISTERED;
        this.token = UUID.randomUUID().toString();
        this.myPosts = new ArrayList<>();
        this.myComments = new ArrayList<>();
        this.numberOfLoginAttempts = 0L;
        this.suggestionList = new ArrayList<>();
        this.suggestionVoteList = new ArrayList<>();

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserStatusEnum getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatusEnum userStatus) {
        this.userStatus = userStatus;
    }

    public List<Post> getMyPosts() {
        return myPosts;
    }

    public void setMyPosts(List<Post> myPosts) {
        this.myPosts = myPosts;
    }

    public List<Comment> getMyComments() {
        return myComments;
    }

    public void setMyComments(List<Comment> myComments) {
        this.myComments = myComments;
    }

    public Long getNumberOfLoginAttempts() {
        return numberOfLoginAttempts;
    }

    public void setNumberOfLoginAttempts(Long numberOfLoginAttempts) {
        this.numberOfLoginAttempts = numberOfLoginAttempts;
    }

    public List<OpenPosition> getOpenPositionList() {
        return openPositionList;
    }

    public void setOpenPositionList(List<OpenPosition> openPositionList) {
        this.openPositionList = openPositionList;
    }

    public List<ApplicantForOpenPosition> getApplicantForOpenPositionList() {
        return applicantForOpenPositionList;
    }

    public void setApplicantForOpenPositionList(List<ApplicantForOpenPosition> applicantForOpenPositionList) {
        this.applicantForOpenPositionList = applicantForOpenPositionList;
    }

    public List<MeetingReservation> getOwnReservation() {
        return ownReservation;
    }

    public void setOwnReservation(List<MeetingReservation> ownReservation) {
        this.ownReservation = ownReservation;
    }

    public List<MeetingParticipent> getAllMeeting() {
        return allMeeting;
    }

    public void setAllMeeting(List<MeetingParticipent> allMeeting) {
        this.allMeeting = allMeeting;
    }

    public List<Suggestion> getSuggestionList() {
        return suggestionList;
    }

    public void setSuggestionList(List<Suggestion> suggestionList) {
        this.suggestionList = suggestionList;
    }

    public List<SuggestionVote> getSuggestionVoteList() {
        return suggestionVoteList;
    }

    public void setSuggestionVoteList(List<SuggestionVote> suggestionVoteList) {
        this.suggestionVoteList = suggestionVoteList;
    }

    public List<TimeOffDateRange> getTimeOffDateRangeList() {
        return timeOffDateRangeList;
    }

    public void setTimeOffDateRangeList(List<TimeOffDateRange> timeOffDateRangeList) {
        this.timeOffDateRangeList = timeOffDateRangeList;
    }
}
