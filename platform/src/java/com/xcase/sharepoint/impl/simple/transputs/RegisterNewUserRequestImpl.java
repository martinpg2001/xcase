/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.transputs;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.transputs.RegisterNewUserRequest;

/**
 * @author martin
 *
 */
public class RegisterNewUserRequestImpl extends SharepointRequestImpl implements RegisterNewUserRequest {

    /**
     * email, to register a new user.
     */
    private String loginName;

    /**
     * password.
     */
    private String password;

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
     * @return the password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return SharepointConstant.ACTION_NAME_REGISTER_NEW_USER;
    }
}
