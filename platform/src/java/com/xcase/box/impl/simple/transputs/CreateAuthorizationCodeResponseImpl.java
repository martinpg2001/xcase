/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.transputs.CreateAuthorizationCodeResponse;

/**
 *
 * @author martin
 */
public class CreateAuthorizationCodeResponseImpl extends BoxResponseImpl implements CreateAuthorizationCodeResponse {

    private String authorizationCode;

    public String getAuthorizationCode() {
        return this.authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
