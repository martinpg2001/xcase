/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.transputs;

import java.util.List;
import org.apache.http.NameValuePair;

/**
 *
 * @author martinpg
 */
public interface InvokeAdvancedRequest extends MSGraphRequest {
    String getAdvancedUrl();
    String getMemberBody();
    String getMethod();
    List<NameValuePair> getParameters();
    void setAdvancedUrl(String advancedUrl);
    void setMemberBody(String memberBody);
    void setMethod(String method);
    void setParameters(List<NameValuePair> parameters);
}
