/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface CreateAuthorizationCodeResponse extends BoxResponse {

    /**
     *
     * @return authorizationCode
     */
    public String getAuthorizationCode();

    /**
     *
     * @param authorizationCode
     */
    public void setAuthorizationCode(String authorizationCode);
}
