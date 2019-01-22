/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.transputs;

/**
 *
 * @author martin
 */
public interface InvokeWebServiceRequest {

    public String getEndpoint();

    public void setEndpoint(String endpoint);

    public String getPassword();

    public void setPassword(String password);
    
    public String getProtocol();

    public void setProtocol(String protocol);

    public String getUsername();

    public void setUsername(String username);
}
