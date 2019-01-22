/**
 * Copyright 2018 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.transputs;

import com.xcase.common.transputs.CommonResponse;
import org.apache.http.StatusLine;

/**
 *
 * @author martinpg
 */
public class CommonResponseImpl implements CommonResponse {
    /**
     * HTTP response code of response.
     */
    protected int responseCode;
    
    /**
     * HTTP response status line.
     */    
    protected StatusLine statusLine;

    public int getResponseCode() {
        return this.responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
    
    public StatusLine getStatusLine() {
        return this.statusLine;
    }
    
    public void setStatusLine(StatusLine statusLine) {
        this.statusLine = statusLine;
    }
}
