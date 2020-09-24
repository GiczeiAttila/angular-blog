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
import com.progmasters.reactblog.domain.PostPictureRegistry;
import com.progmasters.reactblog.domain.PostTypes;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.progmasters.reactblog.config.SpringWebConfig.DATE_TIME_FORMATTER;

public class PostListItem {

    private Long id;
    private String author;
    private String title;
    private String postBodyShortened;
    private String picture;
    private String createdAt;
    private Integer numberOfComments;
    private PostCategories category;
    private PostTypes type;
    private List<CommentDetails> comments;

    public PostListItem(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();

        this.author = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();


        this.postBodyShortened = Stream.of(post.getPostBody())
                .map(string -> string.substring(0, Math.min(200, string.length())))
                .map(string -> string.substring(0, string.contains(" ") && post.getPostBody().length() > 205 ? string.lastIndexOf(" ") : string.length()))
                .map(string -> string.equals(post.getPostBody()) ? string : string.concat("..."))
                .collect(Collectors.joining());


        this.picture = post.getPictureUrl();

//        this.createdAt = post.getCreatedAt()
//                .toLocalDate();
        this.createdAt = DATE_TIME_FORMATTER.format(post.getCreatedAt());
        this.numberOfComments = post.getComments().size();
        this.category = post.getCategory();
        this.type = post.getType();

        if (post.getComments().size() < 4) {
            this.comments = post.getComments().stream()
                    .map(CommentDetails::new)
                    .collect(Collectors.toList());
        } else {
            for (int i = 0; i < 3; i++) {
                this.comments.add(new CommentDetails(post.getComments().get(i)));
            }
        }
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPostBodyShortened() {
        return postBodyShortened;
    }

    public String getPicture() {
        return picture;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Integer getNumberOfComments() {
        return numberOfComments;
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

    public List<CommentDetails> getComments() {
        return comments;
    }
}
