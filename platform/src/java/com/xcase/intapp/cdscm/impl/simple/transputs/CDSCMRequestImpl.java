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

    public String getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
