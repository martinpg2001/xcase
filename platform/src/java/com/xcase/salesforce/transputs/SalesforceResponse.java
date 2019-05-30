/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.transputs;

import com.google.gson.JsonElement;
import org.apache.http.StatusLine;

/**
 *
 * @author martin
 */
public interface SalesforceResponse {
    /**
     * get the entity string.
     *
     * @return the entity string
     */
    public String getEntityString();
    
    public JsonElement getJsonElement();
    
    /**
     * get the message.
     *
     * @return the message
     */
    public String getMessage();
    
    /**
     * get the response code.
     *
     * @return the response
     */
    public int getResponseCode();
    
    /**
     * get the status string.
     *
     * @return the status
     */
    public String getStatus();
    
    /**
     * get the status line.
     *
     * @param status the status to set
     */
    public StatusLine getStatusLine();
    
    /**
     * set the entity string.
     *
     * @param entityString the entityString to set
     */
    public void setEntityString(String entityString);

    /**
     * set the message.
     *
     * @param message the message to set
     */
    public void setMessage(String message);
    
    /**
     * set the response code.
     *
     * @param entityString the response code to set
     */
    public void setResponseCode(int responseCode);

    /**
     * set the status string.
     *
     * @param status the status to set
     */
    public void setStatus(String status);
    
    /**
     * set the status line.
     *
     * @param status the status to set
     */
    public void setStatusLine(StatusLine statusLine);

    public void setJsonElement(JsonElement jsonElement);
}
