/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xcase.open.impl.simple.transputs;

import com.xcase.open.transputs.CreateAuthorizationCodeFromApplicationResponse;

/**
 *
 * @author martin
 */
public class CreateAuthorizationCodeFromApplicationResponseImpl extends OpenRequestImpl implements CreateAuthorizationCodeFromApplicationResponse {
    private String authorizationCode;
    
    public String getAuthorizationCode() {
       return this.authorizationCode;
    }
    
    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
