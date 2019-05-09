/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.intapp.advanced.impl.simple.transputs;

import com.xcase.intapp.advanced.transputs.AdvancedRequest;

/**
 *
 * @author martin
 */
public class AdvancedRequestImpl implements AdvancedRequest {
    private String accept;
    private String accessToken;
    private String operationPath;

    public String getAccept() {
        return this.accept;
    }
    
    public String getAccessToken() {
        return this.accessToken;
    }

    public String getOperationPath() {
        return operationPath;
    }
    
    public void setAccept(String accept) {
        this.accept = accept;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }
}
