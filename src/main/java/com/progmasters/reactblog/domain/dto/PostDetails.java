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

package com.progmasters.reactblog.domain.dto;

import com.progmasters.reactblog.domain.Post;
import com.progmasters.reactblog.domain.PostCategories;
import com.progmasters.reactblog.domain.PostTypes;

import java.util.List;
import java.util.stream.Collectors;

import static com.progmasters.reactblog.config.SpringWebConfig.DATE_TIME_FORMATTER;

public class PostDetails {

    private final Long id;
    private final String author;
    private final String title;
    private final String postBody;
    private final String picture;
    private final String createdAt;
    private final List<CommentDetails> comments;
    private final PostCategories category;
    private final PostTypes type;
    private final AddressDetails address;

    public PostDetails(Post post) {
        this.id = post.getId();
        this.author = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
        this.title = post.getTitle();
        this.postBody = post.getPostBody();
        this.picture = post.getPictureUrl();
        this.createdAt = DATE_TIME_FORMATTER.format(post.getCreatedAt());
        this.comments = post.getComments().stream()
                .map(CommentDetails::new)
                .collect(Collectors.toList());
        this.category = post.getCategory();
        this.type = post.getType();
        this.address = new AddressDetails(post.getAddress());
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPostBody() {
        return postBody;
    }

    public String getPicture() {
        return picture;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public List<CommentDetails> getComments() {
        return comments;
    }

    public String getAuthor() {
        return author;
    }

    public PostCategories getCategory() {
        return category;
    }

    public PostTypes getType() {
        return type;
    }

    public AddressDetails getAddress() {
        return address;
    }

}
