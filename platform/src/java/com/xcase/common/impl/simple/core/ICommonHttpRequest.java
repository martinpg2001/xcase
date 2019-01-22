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
public interface ICommonHttpRequest {
    public String getMethod();
    
    public String getUrl();
    
    public Header[] getHeaders();
    
    public List<NameValuePair> getParameters();
    
    public String getEntity();
    
    public Credentials getCredentials();
    
    public void setMethod(String method);
    
    public void setUrl(String url);
    
    public void setHeaders(Header[] headers);
    
    public void setParameters(List<NameValuePair> parameters);
    
    public void setEntity(String entity);
    
    public void setCredentials(Credentials credentials);
}
