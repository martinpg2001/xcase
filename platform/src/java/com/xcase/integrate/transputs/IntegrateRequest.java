/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.transputs;

/**
 *
 * @author martin
 */
public interface IntegrateRequest {
    
    public String getAccept();

    public void setAccept(String accept);

    public String getAccessToken();

    public void setAccessToken(String accessToken);
    
    public String getEncryptionKey();

    public void setEncryptionKey(String encryptionKey);    
}
