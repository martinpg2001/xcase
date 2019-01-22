/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.InvokeAdvancedRequest;
import java.util.List;
import org.apache.http.NameValuePair;

/**
 *
 * @author martinpg
 */
public class InvokeAdvancedRequestImpl extends MSGraphRequestImpl implements InvokeAdvancedRequest {
    private String advancedUrl;
    private String memberBody;
    private String method;
    private List<NameValuePair> parameters;

    public String getAdvancedUrl() {
        return this.advancedUrl;
    }

    public String getMemberBody() {
        return this.memberBody;
    }

    public String getMethod() {
        return this.method;
    }

    public List<NameValuePair> getParameters() {
        return this.parameters;
    }

    public void setAdvancedUrl(String advancedUrl) {
        this.advancedUrl = advancedUrl;
    }

    public void setMemberBody(String memberBody) {
        this.memberBody = memberBody;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setParameters(List<NameValuePair> parameters) {
        this.parameters = parameters;
    }  
}
