/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.transputs;

/**
 *
 * @author martin
 */
public interface VerifyRegistrationEmailRequest extends SharepointRequest {

    /**
     * @return the loginName
     */
    public String getLoginName();

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName);
}
