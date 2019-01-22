/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.common.impl.simple.core;

import java.lang.invoke.MethodHandles;
import java.util.Properties;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CommonHttpManagerConfig implements ICommonHttpManagerConfig {
    private boolean useClientCertificate = false;
    private String keystorePath;
    private String keystorePass;
    private String keyPass;
    private HttpHost proxy;
    private String supportedProtocols;
    private String userAgent;
    
    /**
     * log4j object.
     */
    static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public CommonHttpManagerConfig(Properties properties) {
        try {
            if (properties.getProperty("supportedprotocols") != null) {
                setSupportedProtocols((String) properties.getProperty("supportedprotocols"));
            }
            
            if (properties.getProperty("useclientcertificate") != null && properties.getProperty("useclientcertificate").equalsIgnoreCase("yes")) {
                useClientCertificate = true;
                String keystorePath = properties.getProperty("keystorepath");
                LOGGER.debug("keystorePath is " + keystorePath);
                setKeystorePath(keystorePath);
                String keystorePass = properties.getProperty("keystorepass");
                LOGGER.debug("keystorePass is " + keystorePass);
                setKeystorePass(keystorePass);
                String keyPass = properties.getProperty("keypass");
                LOGGER.debug("keyPass is " + keyPass);
                setKeyPass(keyPass);
            }
            
            if (properties.getProperty("useragent") != null) {
                setUserAgent((String) properties.getProperty("useragent"));
            }
            
            if (properties.getProperty("proxy") != null && properties.getProperty("proxy").equalsIgnoreCase("yes")) {
                String proxyServer = properties.getProperty("proxyserver");
                LOGGER.debug("proxyServer is " + proxyServer);
                String proxyPortString = properties.getProperty("proxyport");
                LOGGER.debug("proxyPortString is " + proxyPortString);
                Integer proxyPort = Integer.valueOf(proxyPortString);
                LOGGER.debug("proxyPort is " + proxyPort);
                String proxyScheme = properties.getProperty("proxyscheme");
                LOGGER.debug("proxyScheme is " + proxyScheme);
                setProxy(new HttpHost(proxyServer, proxyPort, proxyScheme));
            }
        } catch (Exception e) {
            LOGGER.warn("exception constructing CommonHttpManagerConfig: " + e.getMessage());
            throw e;
        }
    }
    
    public boolean useClientCertificate() {
        return useClientCertificate;
    }
    
    public String getKeystorePath() {
        return this.keystorePath;
    }
    
    public String getKeystorePass() {
        return this.keystorePass;
    }
    
    public String getKeyPass() {
        return this.keyPass;
    }
    
    public HttpHost getProxy() {
        return this.proxy;
    }
    
    public String getSupportedProtocols() {
        return this.supportedProtocols;
    }
    
    public String getUserAgent() {
        return this.userAgent;
    }
    
    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public void setKeystorePass(String keystorePass) {
        this.keystorePass = keystorePass;
    }
    
    public void setKeyPass(String keyPass) {
        this.keyPass = keyPass;
    } 
    
    public void setProxy(HttpHost proxy) {
        this.proxy = proxy;
    }

    public void setSupportedProtocols(String supportedProtocols) {
        this.supportedProtocols = supportedProtocols;
    }
    
    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }    
}
