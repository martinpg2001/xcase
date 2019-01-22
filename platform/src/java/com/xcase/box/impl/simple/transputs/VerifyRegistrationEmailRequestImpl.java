/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.VerifyRegistrationEmailRequest;

/**
 * @author martin
 *
 */
public class VerifyRegistrationEmailRequestImpl extends BoxRequestImpl implements VerifyRegistrationEmailRequest {

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
        return BoxConstant.ACTION_NAME_VERIFY_REGISTRATION_EMAIL;
    }
}
