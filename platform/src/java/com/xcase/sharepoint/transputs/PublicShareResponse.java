/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface PublicShareResponse extends SharepointResponse {

    /**
     * @return the publicName
     */
    public String getPublicName();

    /**
     * @param publicName the publicName to set
     */
    public void setPublicName(String publicName);
}
