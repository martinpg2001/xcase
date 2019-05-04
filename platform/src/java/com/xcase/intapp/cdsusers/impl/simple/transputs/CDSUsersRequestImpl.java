/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.CDSUsersRequest;

/**
 *
 * @author martin
 */
public class CDSUsersRequestImpl implements CDSUsersRequest {

    private String accessToken;
    private String operationPath;

    public String getAccessToken() {
        return this.accessToken;
    }
    
    public String getOperationPath() {
        return operationPath;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }
}
