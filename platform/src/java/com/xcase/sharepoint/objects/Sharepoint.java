/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.objects;

/**
 *
 * @author martin
 */
public interface Sharepoint {

    /**
     * get the box id.
     *
     * @return the id
     */
    public String getId();

    /**
     * set the box id.
     *
     * @param id the id to set
     */
    public void setId(String id);

    /**
     * get box URL.
     *
     * @return the url
     */
    public String getUrl();

    /**
     * set box URL.
     *
     * @param url the url to set
     */
    public void setUrl(String url);

    /**
     * get box status.
     *
     * @return the status
     */
    public String getStatus();

    /**
     * set box status.
     *
     * @param status the status to set
     */
    public void setStatus(String status);
}
