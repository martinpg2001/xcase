/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface PublicShareResponse extends BoxResponse {

    /**
     * @return the publicName
     */
    public String getPublicName();

    /**
     * @param publicName the publicName to set
     */
    public void setPublicName(String publicName);
}
