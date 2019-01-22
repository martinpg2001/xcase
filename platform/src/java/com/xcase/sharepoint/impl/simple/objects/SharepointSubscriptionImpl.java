/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.sharepoint.impl.simple.objects;

import com.xcase.sharepoint.objects.SharepointSubscription;

/**
 * @author martin
 *
 */
public class SharepointSubscriptionImpl implements SharepointSubscription {

    /**
     * Sharepoint id.
     */
    private String boxId;

    /**
     * user name.
     */
    private String userName;

    /**
     * URL.
     */
    private String url;

    /**
     * status.
     */
    private String status;

    /**
     * @return the boxId
     */
    public String getBoxId() {
        return this.boxId;
    }

    /**
     * @param boxId the boxId to set
     */
    public void setBoxId(String boxId) {
        this.boxId = boxId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return this.url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
}
