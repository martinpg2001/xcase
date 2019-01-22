/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface RegisterNewUserRequest extends SharepointRequest {

    /**
     * @return the loginName
     */
    public String getLoginName();

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName);

    /**
     * @return the password
     */
    public String getPassword();

    /**
     * @param password the password to set
     */
    public void setPassword(String password);
}
