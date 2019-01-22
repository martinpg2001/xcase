/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.transputs;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.transputs.CreateUserRequest;

/**
 *
 * @author martin
 */
public class CreateUserRequestImpl extends BoxRequestImpl implements CreateUserRequest {

    private String login;
    private String name;

    /**
     * @return the login
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.login;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return action name
     */
    public String getActionName() {
        return BoxConstant.ACTION_NAME_ADD_TO_MYBOX;
    }
}
