/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.CDSRefDataRequest;

/**
 *
 * @author martin
 */
public class CDSRefDataRequestImpl implements CDSRefDataRequest {

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
