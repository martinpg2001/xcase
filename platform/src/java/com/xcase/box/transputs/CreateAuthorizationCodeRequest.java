/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.transputs;

/**
 *
 * @author martin
 */
public interface CreateAuthorizationCodeRequest extends BoxRequest {

    /**
     *
     * @return password
     */
    public String getPassword();

    /**
     *
     * @param password
     */
    public void setPassword(String password);

    /**
     *
     * @return redirectURI
     */
    public String getRedirectURI();

    /**
     *
     * @param redirectURI
     */
    public void setRedirectURI(String redirectURI);

    /**
     *
     * @return username
     */
    public String getUsername();

    /**
     *
     * @param username
     */
    public void setUsername(String username);
}
