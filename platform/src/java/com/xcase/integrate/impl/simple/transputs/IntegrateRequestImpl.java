/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.transputs;

import com.xcase.integrate.transputs.IntegrateRequest;
/**
 *
 * @author martin
 */
public class IntegrateRequestImpl implements IntegrateRequest {

    private String accept;
    private String accessToken;
    private String encryptionKey;
    
    public String getAccept() {
        return this.accept;
    }

    public String getAccessToken() {
        return this.accessToken;
    }
    
    public String getEncryptionKey() {
        return this.encryptionKey;
    }
    
    public void setAccept(String accept) {
        this.accept = accept;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }
}
