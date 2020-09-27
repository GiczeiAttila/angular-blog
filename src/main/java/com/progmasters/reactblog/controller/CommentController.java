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

package com.progmasters.reactblog.controller;

import com.progmasters.reactblog.domain.Comment;
import com.progmasters.reactblog.domain.Post;
import com.progmasters.reactblog.domain.dto.CommentDetails;
import com.progmasters.reactblog.domain.dto.CommentFormData;
import com.progmasters.reactblog.service.CommentService;
import com.progmasters.reactblog.validator.CommentFormDetailsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    //TODO Review: Ezek a fieldek mehetnek finalra
    private final CommentService commentService;
    private final CommentFormDetailsValidator commentFormDetailsValidator;

    @Autowired
    public CommentController(CommentService commentService,
                             CommentFormDetailsValidator commentFormDetailsValidator
    ) {
        this.commentService = commentService;
        this.commentFormDetailsValidator = commentFormDetailsValidator;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(commentFormDetailsValidator);
    }

    //TODO Review - A ResponseEntitynek mindig adjatok típust, csak hogy ne nyafogjon az IDE/SonarLint
    // Ha tudjátok h nem ad vissza semmit, akkor lehet <Void>
    @PostMapping
    public ResponseEntity createComment(@Valid @RequestBody CommentFormData commentFormData) {
        //TODO Review - Ez a log üzenet nem biztos hogy valid,
        // hiszen itt még simán előfordulhat, hogy a comment nem is fog létrejönni valamiért...
        logger.info("New comment is created");
        //TODO Review - Nem túl jó practice controller layerig felküldeni az entityt, mivel nem ti kezelitek az
        // entityManagert, ezért elég durva anomáliákat tud okozni, mivel itt már kiléptetek a tranzakcióból, de még lehet
        // hogy az entity managed stateben van, de nem biztos...
        Comment commentCreated = commentService.createComment(commentFormData);
        //TODO Review - Szerintem nem túl jó practice, ha a controller layerben van ilyesfajta logika, ha valami
        // félresikerült a service layerben, akkor a legszebb, ha ott mindig dobunk valami exceptiont, ezt pedig
        // lekezeljük a GlobalExceptionHandlerben
        if (commentCreated != null) {
            return new ResponseEntity(HttpStatus.CREATED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<CommentDetails>> findAllCommentsByPostId(@PathVariable("id") Long id) {
        //TODO Review - Lásd az előző methodot...
        Post actualPost = this.commentService.findPostById(id);
        List<CommentDetails> comments;
        if (actualPost != null) {
            comments = this.commentService.findAllComments(id);
            logger.info("Requested comments by post id: " + id);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
