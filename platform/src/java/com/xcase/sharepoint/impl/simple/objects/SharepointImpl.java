/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.sharepoint.impl.simple.objects;

import com.xcase.sharepoint.objects.Sharepoint;

/**
 * @author martin
 *
 */
public class SharepointImpl implements Sharepoint {

    /**
     * Sharepoint id.
     */
    private String id;

    /**
     * Sharepoint URL.
     */
    private String url;

    /**
     * Sharepoint status.
     */
    private String status;

    /**
     * @return the id
     */
    public String getId() {
        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
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
