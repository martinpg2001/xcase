/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.msgraph.impl.simple.transputs;

import com.xcase.msgraph.transputs.GetAuthorizationCodeResponse;

/**
 *
 * @author martin
 */
public class GetAuthorizationCodeResponseImpl extends MSGraphResponseImpl implements GetAuthorizationCodeResponse {
    private String authorizationCode;
    
    public String getAuthorizationCode() {
        return this.authorizationCode;
    }
    
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }    
}
