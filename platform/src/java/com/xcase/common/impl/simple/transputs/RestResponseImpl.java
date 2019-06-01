package com.xcase.common.impl.simple.transputs;

import com.xcase.common.transputs.RestResponse;

import org.apache.http.StatusLine;

public class RestResponseImpl extends CommonResponseImpl implements RestResponse {
    private String entityString;
    
    /**
     * HTTP response code of response.
     */
    private int responseCode;
    
    private String status;
    
    /**
     * HTTP response status line.
     */    
    private StatusLine statusLine;
    
    @Override
    public String getEntityString() {
        return entityString;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public int getResponseCode() {
        return this.responseCode;
    }
    
    @Override
    public StatusLine getStatusLine() {
        return this.statusLine;
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
    public void setEntityString(String entityString) {
        this.entityString = entityString;
    }

    @Override
    public void setStatus(String entityString) {
        this.status = status;
    }

}
