/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.OpenRequest;

/**
 *
 * @author martin
 */
public class OpenRequestImpl implements OpenRequest {
    private String accessToken;
    
    public String getAccessToken() {
        return this.accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
