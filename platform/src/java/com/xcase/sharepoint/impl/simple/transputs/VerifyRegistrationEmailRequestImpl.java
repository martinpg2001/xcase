/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.VerifyRegistrationEmailRequest;

/**
 * @author martin
 *
 */
public class VerifyRegistrationEmailRequestImpl extends SharepointRequestImpl implements VerifyRegistrationEmailRequest {

    /**
     * the email to verify.
     */
    private String loginName;

    /**
     * @return the loginName
     */
    public String getLoginName() {
        return this.loginName;
    }

    /**
     * @param loginName the loginName to set
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return SharepointConstant.ACTION_NAME_VERIFY_REGISTRATION_EMAIL;
    }
}
