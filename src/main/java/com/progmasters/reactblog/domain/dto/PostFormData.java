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

import com.progmasters.reactblog.domain.PostCategories;
import com.progmasters.reactblog.domain.PostTypes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class PostFormData {

    private String title;
    private String authorId;
    private String postBody;
    private CommonsMultipartFile picture;
    private PostCategories category;
    private PostTypes type;
    private AddressFormData address;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public CommonsMultipartFile getPicture() {
        return picture;
    }

    public void setPicture(CommonsMultipartFile picture) {
        this.picture = picture;
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

    public AddressFormData getAddress() {
        return address;
    }

    public void setAddress(AddressFormData address) {
        this.address = address;
    }

}
