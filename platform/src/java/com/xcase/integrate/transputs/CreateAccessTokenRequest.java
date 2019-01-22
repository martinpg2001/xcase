/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martinpg
 */
public interface CreateAccessTokenRequest extends IntegrateRequest {

    public void setUsername(String username);

    public void setPassword(String password);

}
