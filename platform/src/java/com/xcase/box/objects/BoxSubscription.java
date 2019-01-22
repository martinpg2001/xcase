/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.objects;

/**
 *
 * @author martin
 */
public interface BoxSubscription {

    /**
     * @return the boxId
     */
    public String getBoxId();

    /**
     * @param boxId the boxId to set
     */
    public void setBoxId(String boxId);

    /**
     * @return the userName
     */
    public String getUserName();

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName);

    /**
     * @return the url
     */
    public String getUrl();

    /**
     * @param url the url to set
     */
    public void setUrl(String url);

    /**
     * @return the status
     */
    public String getStatus();

    /**
     * @param status the status to set
     */
    public void setStatus(String status);
}
