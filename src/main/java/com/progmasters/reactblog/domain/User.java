package com.progmasters.reactblog.domain;

import java.util.List;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private String telNumber;
    private List<Post> myPosts;
    private List<Comment> myComments;

}
