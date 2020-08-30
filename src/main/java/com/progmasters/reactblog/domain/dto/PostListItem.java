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

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.progmasters.reactblog.config.SpringWebConfig.DATE_TIME_FORMATTER;

public class PostListItem {

    private Long id;
    private String title;
    private String postBodyShortened;
    private String picture;
    private String createdAt;
    private Integer numberOfComments;

    public PostListItem(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();

        this.postBodyShortened = Stream.of(post.getPostBody())
                .map(string -> string.substring(0, Math.min(200, string.length())))
                .map(string -> string.substring(0, string.contains(" ") && post.getPostBody().length() > 205 ? string.lastIndexOf(" ") : string.length()))
                .map(string -> string.equals(post.getPostBody()) ? string : string.concat("..."))
                .collect(Collectors.joining());
    /*
        The expression above gets the body of the posts, cuts it at 200 characters if it is longer, cuts off the last
        word (which is probably cut in half already) and puts '...' at the end, than sets this as postBodyShortened.
        An alternative method for this without lambda expressions would look like this:

        String postBody = post.getPostBody();
        boolean isPostBodyChanged = false;
        if (postBody.length() > 210) {
            postBody = postBody.substring(0, 200);
            if (postBody.contains(" ")) {
                int lastSpaceIndex = postBody.lastIndexOf(" ");
                postBody = postBody.substring(0, lastSpaceIndex);
            }
            isPostBodyChanged = true;
        }
        if (isPostBodyChanged) {
            postBody = postBody.concat("...");
        }
        this.postBodyShortened = postBody;
    */

        this.picture = post.getPicture();
        this.createdAt = DATE_TIME_FORMATTER.format(post.getCreatedAt());
        this.numberOfComments = post.getComments().size();
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
}
