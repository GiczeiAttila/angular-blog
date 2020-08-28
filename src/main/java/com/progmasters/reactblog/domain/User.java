package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.UserFormDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private String telNumber;

    @OneToMany(mappedBy = "author")
    private List<Post> myPosts;

    @OneToMany
    private List<Comment> myComments;

    public User() {
    }

    public User(UserFormDto userFormDto) {
        this.id = userFormDto.getId();
        this.name = userFormDto.getName();
        this.email = userFormDto.getEmail();
        this.password = userFormDto.getPassword();
        this.telNumber = userFormDto.getTelNumber();
        this.myPosts = new ArrayList<>();
        this.myComments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
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
}
