/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.common.impl.simple.core;

import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.auth.Credentials;

/**
 *
 * @author martinpg
 */
public class CommonHttpRequest implements ICommonHttpRequest{
    private String method;
    private String url;
    private Header[] headers;
    private List<NameValuePair> parameters;
    private String entity;
    private Credentials credentials;
    
    public String getMethod() {
        return method;
    }
    
    public String getUrl() {
        return this.url;
    }
    
    public Header[] getHeaders() {
        return this.headers;
    }
    
    public List<NameValuePair> getParameters() {
        return this.parameters;
    }
    
    public String getEntity() {
        return this.entity;
    }
    
    public Credentials getCredentials() {
        return this.credentials;
    }
    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }
    
    public void setParameters(List<NameValuePair> parameters) {
        this.parameters = parameters;
    }
    
    public void setEntity(String entity) {
        this.entity = entity;
    }
    
    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
