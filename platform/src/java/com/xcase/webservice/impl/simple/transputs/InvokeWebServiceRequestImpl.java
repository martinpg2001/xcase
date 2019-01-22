/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.webservice.impl.simple.transputs;

import com.xcase.webservice.transputs.InvokeWebServiceRequest;

/**
 *
 * @author martin
 */
public class InvokeWebServiceRequestImpl implements InvokeWebServiceRequest {

    private String endpoint;
    private String password;
    private String protocol;
    private String username;

    public String getEndpoint() {
        return this.endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getProtocol() {
        return this.protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
