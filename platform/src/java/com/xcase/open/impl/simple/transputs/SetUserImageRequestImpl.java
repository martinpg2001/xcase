/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.SetUserImageRequest;

public class SetUserImageRequestImpl implements SetUserImageRequest {

    private String userId;
    private String image;

    @Override
    public String getUserId() {
        return this.userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getImage() {
        return this.image;
    }

    @Override
    public void setImage(String image) {
        this.image = image;
    }

}
