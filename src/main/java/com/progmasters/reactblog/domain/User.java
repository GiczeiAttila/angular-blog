package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.UserFormDto;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class User {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;

    @OneToMany(mappedBy = "author")
    private List<Post> myPosts;

//    @OneToMany(mappedBy = "author")
//    private List<Comment> myComments;

    public User() {
    }

    public User(UserFormDto userFormDto) {
        this.id = userFormDto.getId();
        this.firstName = userFormDto.getFirstName();
        this.lastName = userFormDto.getLastName();
        this.email = userFormDto.getEmail();
        this.password = userFormDto.getPassword();
        this.phoneNumber = userFormDto.getPhoneNumber();
//        this.myPosts = new ArrayList<>();
//        this.myComments = new ArrayList<>();
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

    //    public List<Post> getMyPosts() {
//        return myPosts;
//    }
//
//    public void setMyPosts(List<Post> myPosts) {
//        this.myPosts = myPosts;
//    }

//    public List<Comment> getMyComments() {
//        return myComments;
//    }
//
//    public void setMyComments(List<Comment> myComments) {
//        this.myComments = myComments;
//    }
}
