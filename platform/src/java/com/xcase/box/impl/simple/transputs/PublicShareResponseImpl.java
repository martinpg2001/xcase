/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.PublicShareResponse;

/**
 * @author martin
 *
 */
public class PublicShareResponseImpl extends BoxResponseImpl implements
        PublicShareResponse {

    /**
     * public name.
     */
    private String publicName;

    /**
     * @return the publicName
     */
    public String getPublicName() {
        return this.publicName;
    }

    /**
     * @param publicName the publicName to set
     */
    public void setPublicName(String publicName) {
        this.publicName = publicName;
    }

}
