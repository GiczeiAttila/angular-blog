/*
 * Copyright © Progmasters (QTC Kft.), 2018.
 * All rights reserved. No part or the whole of this Teaching Material (TM) may be reproduced, copied, distributed,
 * publicly performed, disseminated to the public, adapted or transmitted in any form or by any means, including
 * photocopying, recording, or other electronic or mechanical methods, without the prior written permission of QTC Kft.
 * This TM may only be used for the purposes of teaching exclusively by QTC Kft. and studying exclusively by QTC Kft.’s
 * students and for no other purposes by any parties other than QTC Kft.
 * This TM shall be kept confidential and shall not be made public or made available or disclosed to any unauthorized person.
 * Any dispute or claim arising out of the breach of these provisions shall be governed by and construed in accordance with the laws of Hungary.
 */

package com.progmasters.reactblog.domain;

import com.progmasters.reactblog.domain.dto.PostFormData;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(name = "title")
    private String title;

    @Column(name = "post_body", columnDefinition = "TEXT")
    private String postBody;

    @Column(name = "img_url", columnDefinition = "TEXT")
    private String picture;

    @Column(name = "creation_at")
    private LocalDateTime createdAt;

    //private ZonedDateTime createdAt;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER)
    @OrderBy(value = "createdAt desc")
    private List<Comment> comments;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private PostCategories category;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private PostTypes type;

    @Embedded
    private Address address;


    public Post() {
    }

    public Post(PostFormData postFormData, User user) {
        this.author = user;
        this.title = postFormData.getTitle();
        this.postBody = postFormData.getPostBody();
        this.picture = postFormData.getPicture();


        /*this.createdAt = ZonedDateTime.of(
                LocalDate.now(),
                LocalTime.MIN,
                ZoneId.of("UTC")
        );

         */

        this.createdAt = LocalDateTime.now(ZoneOffset.UTC);
        this.category = postFormData.getCategory();
        this.type = postFormData.getType();
        this.address = new Address(postFormData.getAddress());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String imgUrl) {
        this.picture = imgUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public PostCategories getCategory() {
        return category;
    }

    public void setCategory(PostCategories category) {
        this.category = category;
    }

    public PostTypes getType() {
        return type;
    }

    public void setType(PostTypes type) {
        this.type = type;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        return id != null ? id.equals(post.id) : post.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
