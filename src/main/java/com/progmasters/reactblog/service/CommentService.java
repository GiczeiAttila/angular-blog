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

package com.progmasters.reactblog.service;

import com.progmasters.reactblog.domain.Comment;
import com.progmasters.reactblog.domain.Post;
import com.progmasters.reactblog.domain.User;
import com.progmasters.reactblog.domain.dto.CommentDetails;
import com.progmasters.reactblog.domain.dto.CommentFormData;
import com.progmasters.reactblog.repository.CommentRepository;
import com.progmasters.reactblog.repository.PostRepository;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository,
                          PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public Comment createComment(CommentFormData commentFormData) {
        Comment result = null;
        Post postToComment = postRepository.findById(commentFormData.getPostId()).orElse(null);
        User user = userRepository.findById(commentFormData.getAuthorId()).orElse(null);
        if (postToComment != null && user != null) {
            result = commentRepository.save(new Comment(commentFormData, postToComment, user));
        }
        return result;
    }

    public Post findPostById(Long id) {
        Optional<Post> optionalPost = this.postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post actualPost = optionalPost.get();
            return actualPost;
        } else {
            return null;
        }

    }


    public List<CommentDetails> findAllComments(Long id) {
        return this.commentRepository.findCommentsByPostId(id)
                .stream()
                .map(CommentDetails::new)
                .collect(Collectors.toList());
    }
}
