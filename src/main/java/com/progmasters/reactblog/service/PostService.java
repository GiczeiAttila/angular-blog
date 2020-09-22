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

import com.progmasters.reactblog.domain.*;
import com.progmasters.reactblog.domain.dto.*;
import com.progmasters.reactblog.repository.PostRepository;
import com.progmasters.reactblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private final EmailSenderService emailSenderService;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, EmailSenderService emailSenderService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.emailSenderService = emailSenderService;
    }

    public Post createPost(PostFormData postFormData) {
        User user = this.userRepository.findById(postFormData.getAuthorId()).orElse(null);
        if (user != null) {
            Post post = postRepository.save(new Post(postFormData, user));
            List<User> userList = userRepository.findAllByStatus(UserStatusEnum.ACTIVE);
            emailSenderService.sendNewPostNotificationEmail(post,userList);
            return post;
        }
        return null;
    }

    public List<PostListItem> getPostListItems() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(PostListItem::new)
                .collect(Collectors.toList());
    }

    public PostDetails getPostDetailsById(Long id) {
        Optional<Post> optionalPostDetails = postRepository.findById(id);
        if (optionalPostDetails.isPresent()) {
            return new PostDetails(optionalPostDetails.get());
        }
        return null;

       /* PostDetails details = null;
        Post post = postRepository.findById(id).orElse(null);
        if (post != null) {
            details = new PostDetails(postRepository.getOne(id));
        }
        return details;

        */
    }

    public PostFormInitData createFormInitData() {
        return new PostFormInitData(
                Arrays.stream(PostCategories.values()).map(CategoryOption::new).collect(Collectors.toList()),
                Arrays.stream(PostTypes.values()).map(TypeOption::new).collect(Collectors.toList())
        );

    }
}
