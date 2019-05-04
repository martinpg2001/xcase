/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.CDSCMRequest;

/**
 *
 * @author martin
 */
public class CDSCMRequestImpl implements CDSCMRequest {

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
