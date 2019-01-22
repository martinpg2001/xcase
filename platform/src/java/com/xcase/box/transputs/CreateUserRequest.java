/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface CreateUserRequest extends BoxRequest {

    /**
     * @return the name
     */
    public String getName();

    /**
     * @param name the name to set
     */
    public void setName(String name);

    /**
     * @return the login
     */
    public String getLogin();

    /**
     * @param login the login to set
     */
    public void setLogin(String login);
}
