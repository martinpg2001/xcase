/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.transputs;

import com.google.gson.JsonElement;
import com.xcase.salesforce.transputs.SalesforceResponse;
import org.apache.http.StatusLine;

/**
 *
 * @author martin
 */
public class SalesforceResponseImpl implements SalesforceResponse {
    private String entityString;
    private JsonElement jsonElement;
    private String message;
    private int responseCode;
    private String status;
    private StatusLine statusLine;

    /**
     * get the entity string.
     *
     * @return the entity string
     */
    public String getEntityString() {
        return entityString;
    }
    
    @Override
    public JsonElement getJsonElement() {
        return jsonElement;
    }
    
    /**
     * get the status string.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }
    
    /**
     * set the entity string.
     *
     * @param entityString the entity string to set
     */
    public void setEntityString(String entityString) {
        this.entityString = entityString;
    }

    /**
     * set the status string.
     *
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getResponseCode() {
        return responseCode;
    }

    @Override
    public StatusLine getStatusLine() {
        return statusLine;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;        
    }

    @Override
    public void setJsonElement(JsonElement jsonElement) {
        this.jsonElement = jsonElement;        
    }
}
