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
import com.progmasters.reactblog.service.cloudinary.CloudinaryFileUploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PostService {
    private static final Logger logger = LoggerFactory.getLogger(PostService.class);
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CloudinaryFileUploader cloudinaryFileUploader;
    private final EmailSenderService emailSenderService;

    private final Jackson2ObjectMapperBuilder builder;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, CloudinaryFileUploader cloudinaryFileUploader, EmailSenderService emailSenderService, Jackson2ObjectMapperBuilder builder) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.cloudinaryFileUploader = cloudinaryFileUploader;
        this.emailSenderService = emailSenderService;
        this.builder = builder;
    }


    public List<PostListItem> getPostListItems() {
        return postRepository.findAllByOrderByCreatedAtDesc()
                .stream().filter(post -> post.getStatus() == PostStatus.ACTIVE)
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

    public Long createPostWithImage(PostFormData data) throws IOException {
        Long authorId = Long.parseLong(data.getAuthorId());
        User user = this.userRepository.findById(authorId).orElse(null);
        if (user != null) {

            Post post = new Post(data, user);
            List<User> userList = userRepository.findAllByStatus(UserStatusEnum.ACTIVE);

            if (data.getPicture() != null) {
                String uploadedFileUrl = cloudinaryFileUploader.processFile(data.getPicture(), data.getTitle(), data.getCategory());
                post.setPictureUrl(uploadedFileUrl);
            }
            post = postRepository.save(post);
            emailSenderService.sendNewPostNotificationEmail(post, userList);
            return post.getId();
        }
        return null;
    }

    public boolean deletePostWithId(Long id) {
        Optional<Post> postOptional = postRepository.findById(id);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            post.setStatus(PostStatus.ARCHIVE);
            logger.info("Post status changed to Archive with post id: " + post.getId());
            return true;
        }
        return false;
    }

    public PostFormData getPostFormDataById(Long id) {
        Optional<Post> optionalPostDetails = postRepository.findById(id);
        if (optionalPostDetails.isPresent()) {
            return new PostFormData(optionalPostDetails.get());
        }
        return null;
    }


    public Long updatePostWithImage(PostFormData data, Long postId) throws IOException {
        Long authorId = Long.parseLong(data.getAuthorId());
        User user = this.userRepository.findById(authorId).orElse(null);
        if (user != null) {
            Post originalPost = postRepository.findById(postId).orElse(null);
            if (originalPost != null) {
                Post post = new Post(data, user);
                List<User> userList = userRepository.findAllByStatus(UserStatusEnum.ACTIVE);
                post.setId(postId);
                if (data.getPicture() != null) {
                    String uploadedFileUrl = cloudinaryFileUploader.processFile(data.getPicture(), data.getTitle(), data.getCategory());
                    post.setPictureUrl(uploadedFileUrl);
                } else {
                    post.setPictureUrl(originalPost.getPictureUrl());
                }
                post = postRepository.save(post);
                emailSenderService.sendNewPostNotificationEmail(post, userList);
                return post.getId();
            }

        }
        return null;
    }
}
