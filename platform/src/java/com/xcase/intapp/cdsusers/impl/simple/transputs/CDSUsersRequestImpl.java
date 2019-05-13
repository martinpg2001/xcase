/**
 * Copyright 2019 Xcase All rights reserved.
 */
package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.CDSUsersRequest;

/**
 *
 * @author martin
 */
public class CDSUsersRequestImpl implements CDSUsersRequest {

    private String accessToken;

    public String getAccessToken() {
        return this.accessToken;
    }
    
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
