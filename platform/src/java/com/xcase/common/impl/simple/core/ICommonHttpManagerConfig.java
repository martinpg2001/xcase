/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.core;

import org.apache.http.HttpHost;

/**
 *
 * @author martin
 */
public interface ICommonHttpManagerConfig {
    public boolean useClientCertificate();
    
    public String getKeystorePath();
    
    public String getKeystorePass();
    
    public String getKeyPass();
    
    public HttpHost getProxy();
    
    public String getSupportedProtocols();
    
    public String getUserAgent();
    
    public void setKeystorePath(String keystorePath);

    public void setKeystorePass(String keystorePass);
    
    public void setKeyPass(String keyPass); 
    
    public void setProxy(HttpHost proxy);

    public void setSupportedProtocols(String supportedProtocols);
    
    public void setUserAgent(String userAgent); 
}
