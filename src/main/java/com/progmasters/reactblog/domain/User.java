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

    public User() {
    }

    public User(UserFormDto userFormDto) {
        this.id = userFormDto.getId();
        this.firstName = userFormDto.getFirstName();
        this.lastName = userFormDto.getLastName();
        this.email = userFormDto.getEmail();
        //this.password = userFormDto.getPassword();
        this.phoneNumber = userFormDto.getPhoneNumber();
        this.userStatus = UserStatusEnum.REGISTERED;
        this.token =  UUID.randomUUID().toString();
        this.myPosts = new ArrayList<>();
        this.myComments = new ArrayList<>();
        this.numberOfLoginAttempts = 0L;
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
}
