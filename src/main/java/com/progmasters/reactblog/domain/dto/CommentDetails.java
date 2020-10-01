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

import com.progmasters.reactblog.domain.Comment;
import com.progmasters.reactblog.utils.DateUtils;

import java.time.LocalDateTime;

public class CommentDetails {

    private Long id;
    private Long authorId;
    private final String author;
    private final String commentBody;
    private final LocalDateTime createdAt;

    public CommentDetails(Comment comment) {
        this.id = comment.getId();
        this.authorId = comment.getAuthor().getId();
        this.author = comment.getAuthor().getFirstName() + " " + comment.getAuthor().getLastName();
        this.commentBody = comment.getCommentBody();
        this.createdAt = DateUtils.localizeDateTimeFromZonedDateTime(comment.getCreatedAt());
    }

    public Long getAuthorId() {
        return authorId;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
